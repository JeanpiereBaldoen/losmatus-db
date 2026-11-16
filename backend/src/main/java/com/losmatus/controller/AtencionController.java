package com.losmatus.controller;

import com.losmatus.entity.Atencion;
import com.losmatus.repository.AtencionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/atenciones")
public class AtencionController extends CrudBaseController<Atencion> {

    public AtencionController(AtencionRepository repositorio) {
        super(repositorio, Atencion.class);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public ResponseEntity<Atencion> crear(Atencion datos) {
        return super.crear(datos);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public Atencion actualizar(Long id, Atencion datos) {
        return super.actualizar(id, datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(Long id) {
        return super.eliminar(id);
    }
}
