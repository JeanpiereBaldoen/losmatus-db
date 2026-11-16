package com.losmatus.controller;

import com.losmatus.entity.EstadoSolicitudServicio;
import com.losmatus.repository.EstadoSolicitudServicioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estados-solicitud")
public class EstadoSolicitudServicioController extends CrudBaseController<EstadoSolicitudServicio> {

    public EstadoSolicitudServicioController(EstadoSolicitudServicioRepository repositorio) {
        super(repositorio, EstadoSolicitudServicio.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstadoSolicitudServicio> crear(EstadoSolicitudServicio datos) {
        return super.crear(datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public EstadoSolicitudServicio actualizar(Long id, EstadoSolicitudServicio datos) {
        return super.actualizar(id, datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(Long id) {
        return super.eliminar(id);
    }
}
