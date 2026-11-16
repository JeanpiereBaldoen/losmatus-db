package com.losmatus.controller;

import com.losmatus.entity.EstadoIncidencia;
import com.losmatus.repository.EstadoIncidenciaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estados-incidencia")
public class EstadoIncidenciaController extends CrudBaseController<EstadoIncidencia> {

    public EstadoIncidenciaController(EstadoIncidenciaRepository repositorio) {
        super(repositorio, EstadoIncidencia.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstadoIncidencia> crear(EstadoIncidencia datos) {
        return super.crear(datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public EstadoIncidencia actualizar(Long id, EstadoIncidencia datos) {
        return super.actualizar(id, datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(Long id) {
        return super.eliminar(id);
    }
}
