package com.losmatus.controller;

import com.losmatus.entity.Equipo;
import com.losmatus.repository.EquipoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController extends CrudBaseController<Equipo> {

    public EquipoController(EquipoRepository repositorio) {
        super(repositorio, Equipo.class);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public ResponseEntity<Equipo> crear(Equipo datos) {
        return super.crear(datos);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public Equipo actualizar(Long id, Equipo datos) {
        return super.actualizar(id, datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(Long id) {
        return super.eliminar(id);
    }
}
