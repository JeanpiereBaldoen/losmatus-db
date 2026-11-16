package com.losmatus.service;

import com.losmatus.config.SecurityUtils;
import com.losmatus.dto.IncidenciaRequest;
import com.losmatus.entity.ConfiguracionSla;
import com.losmatus.entity.HistorialIncidencia;
import com.losmatus.entity.Incidencia;
import com.losmatus.exception.ForbiddenException;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.ConfiguracionSlaRepository;
import com.losmatus.repository.HistorialIncidenciaRepository;
import com.losmatus.repository.IncidenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final HistorialIncidenciaRepository historialIncidenciaRepository;
    private final ConfiguracionSlaRepository configuracionSlaRepository;

    public IncidenciaService(
            IncidenciaRepository incidenciaRepository,
            HistorialIncidenciaRepository historialIncidenciaRepository,
            ConfiguracionSlaRepository configuracionSlaRepository
    ) {
        this.incidenciaRepository = incidenciaRepository;
        this.historialIncidenciaRepository = historialIncidenciaRepository;
        this.configuracionSlaRepository = configuracionSlaRepository;
    }

    // 1. Listar todas las incidencias (con aislamiento de datos para CLIENTE)
    @Transactional(readOnly = true)
    public List<Incidencia> listarTodas() {
        if (SecurityUtils.isCliente()) {
            return incidenciaRepository.findByIdUsuarioReporta(SecurityUtils.getCurrentUserId());
        }
        return incidenciaRepository.findAll();
    }

    // 2. Buscar incidencia por ID (con validacion de propiedad para CLIENTE)
    @Transactional(readOnly = true)
    public Incidencia buscarPorId(Long id) {
        Incidencia incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidencia no encontrada con ID: " + id));

        validarAccesoIncidencia(incidencia);
        return incidencia;
    }

    // 3. Crear una nueva incidencia
    @Transactional
    public Incidencia crear(IncidenciaRequest datos) {
        Incidencia incidencia = new Incidencia();

        incidencia.setCodigo(datos.codigo().trim());
        incidencia.setDescripcion(datos.descripcion().trim());
        incidencia.setIdCategoria(datos.idCategoria());
        incidencia.setIdEstadoIncidencia(datos.idEstadoIncidencia());
        incidencia.setIdSeveridad(datos.idSeveridad());

        // Si es CLIENTE, reporta a su propio nombre obligatoriamente
        if (SecurityUtils.isCliente()) {
            incidencia.setIdUsuarioReporta(SecurityUtils.getCurrentUserId());
        } else {
            incidencia.setIdUsuarioReporta(datos.idUsuarioReporta() != null ? datos.idUsuarioReporta() : SecurityUtils.getCurrentUserId());
        }

        LocalDateTime ahora = LocalDateTime.now();
        incidencia.setFechaReporte(ahora);

        // Calcular SLA si existe configuracion para la severidad
        if (datos.idSeveridad() != null) {
            configuracionSlaRepository.findByIdUsuarioAndIdSeveridad(incidencia.getIdUsuarioReporta(), datos.idSeveridad())
                    .ifPresent(sla -> {
                        if (sla.getTiempoResolucionHoras() > 0) {
                            incidencia.setFechaLimiteResolucion(ahora.plusHours(sla.getTiempoResolucionHoras()));
                        }
                    });
        }

        Incidencia guardada = incidenciaRepository.save(incidencia);

        // Registrar primer estado en el historial
        registrarHistorial(guardada.getIdIncidencia(), guardada.getIdEstadoIncidencia(), guardada.getIdUsuarioReporta());

        return guardada;
    }

    // 4. Actualizar una incidencia existente
    @Transactional
    public Incidencia actualizar(Long id, IncidenciaRequest datos) {
        Incidencia incidencia = buscarPorId(id);

        Long estadoAnterior = incidencia.getIdEstadoIncidencia();

        incidencia.setCodigo(datos.codigo().trim());
        incidencia.setDescripcion(datos.descripcion().trim());
        incidencia.setIdCategoria(datos.idCategoria());
        incidencia.setIdSeveridad(datos.idSeveridad());

        if (!SecurityUtils.isCliente()) {
            incidencia.setIdEstadoIncidencia(datos.idEstadoIncidencia());
            if (datos.idUsuarioReporta() != null) {
                incidencia.setIdUsuarioReporta(datos.idUsuarioReporta());
            }
        }

        // Si cambio el estado, registrar en historial
        if (datos.idEstadoIncidencia() != null && !datos.idEstadoIncidencia().equals(estadoAnterior)) {
            incidencia.setIdEstadoIncidencia(datos.idEstadoIncidencia());
            registrarHistorial(incidencia.getIdIncidencia(), datos.idEstadoIncidencia(), SecurityUtils.getCurrentUserId());

            // Si es resuelta (ID 3 = Resuelta en seed data), fijar fecha finalizacion
            if (datos.idEstadoIncidencia() == 3L) {
                incidencia.setFechaFinalizacion(LocalDateTime.now());
            }
        }

        return incidenciaRepository.save(incidencia);
    }

    // 5. Cambiar estado de la incidencia con historial
    @Transactional
    public Incidencia cambiarEstado(Long id, Long nuevoEstadoId) {
        Incidencia incidencia = buscarPorId(id);

        if (SecurityUtils.isCliente()) {
            throw new ForbiddenException("Los clientes no pueden modificar directamente el estado de una incidencia");
        }

        Long estadoAnterior = incidencia.getIdEstadoIncidencia();
        if (!nuevoEstadoId.equals(estadoAnterior)) {
            incidencia.setIdEstadoIncidencia(nuevoEstadoId);
            registrarHistorial(incidencia.getIdIncidencia(), nuevoEstadoId, SecurityUtils.getCurrentUserId());

            if (nuevoEstadoId == 3L) {
                incidencia.setFechaFinalizacion(LocalDateTime.now());
            }
            if (nuevoEstadoId == 2L && incidencia.getFechaInicioAtencion() == null) {
                incidencia.setFechaInicioAtencion(LocalDateTime.now());
            }
        }

        return incidenciaRepository.save(incidencia);
    }

    public void validarAccesoIncidencia(Incidencia incidencia) {
        if (SecurityUtils.isCliente()) {
            Long usuarioActual = SecurityUtils.getCurrentUserId();
            if (!usuarioActual.equals(incidencia.getIdUsuarioReporta())) {
                throw new ForbiddenException("No tiene permisos para acceder a esta incidencia");
            }
        }
    }

    private void registrarHistorial(Long idIncidencia, Long idEstado, Long idUsuario) {
        HistorialIncidencia historial = new HistorialIncidencia();
        historial.setIdIncidencia(idIncidencia);
        historial.setIdEstadoIncidencia(idEstado);
        historial.setIdUsuario(idUsuario);
        historial.setFechaCambio(LocalDateTime.now());
        historialIncidenciaRepository.save(historial);
    }
}
