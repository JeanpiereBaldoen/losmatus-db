package com.losmatus.service;

import com.losmatus.dto.IncidenciaRequest;
import com.losmatus.entity.Incidencia;
import com.losmatus.repository.IncidenciaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;

    // Constructor
    public IncidenciaService(IncidenciaRepository incidenciaRepository) {
        this.incidenciaRepository = incidenciaRepository;
    }

    // 1. Listar todas las incidencias
    @Transactional(readOnly = true)
    public List<Incidencia> listarTodas() {
        return incidenciaRepository.findAll();
    }

    // 2. Buscar incidencia por ID
    @Transactional(readOnly = true)
    public Incidencia buscarPorId(Long id) {
        return incidenciaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Incidencia no encontrada con ID: " + id));
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
        incidencia.setIdUsuarioReporta(datos.idUsuarioReporta());
        incidencia.setFechaReporte(LocalDateTime.now());

        return incidenciaRepository.save(incidencia);
    }

    // 4. Actualizar una incidencia existente
    @Transactional
    public Incidencia actualizar(Long id, IncidenciaRequest datos) {

        // Buscar la incidencia
        Incidencia incidencia = buscarPorId(id);

        // Actualizar los datos
        incidencia.setCodigo(datos.codigo().trim());
        incidencia.setDescripcion(datos.descripcion().trim());
        incidencia.setIdCategoria(datos.idCategoria());
        incidencia.setIdEstadoIncidencia(datos.idEstadoIncidencia());
        incidencia.setIdSeveridad(datos.idSeveridad());
        incidencia.setIdUsuarioReporta(datos.idUsuarioReporta());

        // Guardar los cambios
        return incidenciaRepository.save(incidencia);
    }
}