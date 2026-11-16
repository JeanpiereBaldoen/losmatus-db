package com.losmatus.service;

import com.losmatus.config.SecurityUtils;
import com.losmatus.dto.SolicitudServicioRequest;
import com.losmatus.entity.HistorialSolicitudServicio;
import com.losmatus.entity.SolicitudServicio;
import com.losmatus.exception.ForbiddenException;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.HistorialSolicitudServicioRepository;
import com.losmatus.repository.SolicitudServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Service
public class SolicitudServicioService {

    private final SolicitudServicioRepository repo;
    private final HistorialSolicitudServicioRepository historialRepo;

    public SolicitudServicioService(
            SolicitudServicioRepository repo,
            HistorialSolicitudServicioRepository historialRepo
    ) {
        this.repo = repo;
        this.historialRepo = historialRepo;
    }

    @Transactional(readOnly = true)
    public List<SolicitudServicio> listarTodas() {
        if (SecurityUtils.isCliente()) {
            return repo.findByIdUsuario(SecurityUtils.getCurrentUserId());
        }
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public SolicitudServicio buscarPorId(Long id) {
        SolicitudServicio solicitud = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada con ID: " + id));

        validarAcceso(solicitud);
        return solicitud;
    }

    @Transactional
    public SolicitudServicio crear(SolicitudServicioRequest request) {
        SolicitudServicio solicitud = new SolicitudServicio();

        int anio = Year.now().getValue();
        int correlativo = repo.findMaxCorrelativoByAnio(anio).orElse(0) + 1;
        String codigo = String.format("SOL-%d-%04d", anio, correlativo);

        solicitud.setAnio(anio);
        solicitud.setCorrelativo(correlativo);
        solicitud.setCodigo(codigo);
        solicitud.setDescripcion(request.descripcion() != null ? request.descripcion().trim() : null);
        solicitud.setIdTipoServicio(request.idTipoServicio());
        solicitud.setIdSeveridad(request.idSeveridad());
        solicitud.setIdEstado(request.idEstado() != null ? request.idEstado() : 1L); // 1 = Pendiente
        solicitud.setRequiereCotizacion(Boolean.TRUE.equals(request.requiereCotizacion()));
        solicitud.setFechaSolicitud(LocalDateTime.now());

        if (SecurityUtils.isCliente()) {
            solicitud.setIdUsuario(SecurityUtils.getCurrentUserId());
        } else {
            solicitud.setIdUsuario(request.idUsuario() != null ? request.idUsuario() : SecurityUtils.getCurrentUserId());
        }

        SolicitudServicio guardada = repo.save(solicitud);

        // Registrar primer historial
        registrarHistorial(guardada.getIdSolicitud(), guardada.getIdEstado(), guardada.getIdSeveridad(), "Creacion de solicitud", guardada.getIdUsuario());

        return guardada;
    }

    @Transactional
    public SolicitudServicio actualizar(Long id, SolicitudServicioRequest request) {
        SolicitudServicio solicitud = buscarPorId(id);

        Long estadoAnterior = solicitud.getIdEstado();
        Long severidadAnterior = solicitud.getIdSeveridad();

        solicitud.setDescripcion(request.descripcion() != null ? request.descripcion().trim() : solicitud.getDescripcion());
        solicitud.setIdTipoServicio(request.idTipoServicio());
        solicitud.setIdSeveridad(request.idSeveridad());

        if (!SecurityUtils.isCliente()) {
            if (request.idEstado() != null) {
                solicitud.setIdEstado(request.idEstado());
            }
            if (request.requiereCotizacion() != null) {
                solicitud.setRequiereCotizacion(request.requiereCotizacion());
            }
            if (request.idUsuario() != null) {
                solicitud.setIdUsuario(request.idUsuario());
            }
        }

        if (!solicitud.getIdEstado().equals(estadoAnterior) || !solicitud.getIdSeveridad().equals(severidadAnterior)) {
            registrarHistorial(solicitud.getIdSolicitud(), solicitud.getIdEstado(), solicitud.getIdSeveridad(), "Actualizacion de solicitud", SecurityUtils.getCurrentUserId());
        }

        return repo.save(solicitud);
    }

    @Transactional
    public SolicitudServicio cambiarEstado(Long id, Long nuevoEstadoId, String detalle) {
        SolicitudServicio solicitud = buscarPorId(id);

        if (SecurityUtils.isCliente()) {
            throw new ForbiddenException("Los clientes no pueden modificar directamente el estado de una solicitud");
        }

        if (!nuevoEstadoId.equals(solicitud.getIdEstado())) {
            solicitud.setIdEstado(nuevoEstadoId);
            registrarHistorial(solicitud.getIdSolicitud(), nuevoEstadoId, solicitud.getIdSeveridad(), detalle != null ? detalle : "Cambio de estado", SecurityUtils.getCurrentUserId());
        }

        return repo.save(solicitud);
    }

    public void validarAcceso(SolicitudServicio solicitud) {
        if (SecurityUtils.isCliente()) {
            Long usuarioActual = SecurityUtils.getCurrentUserId();
            if (!usuarioActual.equals(solicitud.getIdUsuario())) {
                throw new ForbiddenException("No tiene permisos para acceder a esta solicitud");
            }
        }
    }

    private void registrarHistorial(Long idSolicitud, Long idEstado, Long idSeveridad, String detalle, Long idUsuario) {
        HistorialSolicitudServicio hist = new HistorialSolicitudServicio();
        hist.setIdSolicitud(idSolicitud);
        hist.setIdEstado(idEstado);
        hist.setIdSeveridad(idSeveridad);
        hist.setDetalle(detalle);
        hist.setIdUsuario(idUsuario);
        hist.setFechaCambio(LocalDateTime.now());
        historialRepo.save(hist);
    }
}

