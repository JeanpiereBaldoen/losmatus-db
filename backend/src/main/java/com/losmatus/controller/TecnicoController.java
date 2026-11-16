package com.losmatus.controller;

import com.losmatus.entity.Tecnico;
import com.losmatus.repository.TecnicoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController extends CrudBaseController<Tecnico> {

    public TecnicoController(TecnicoRepository repositorio) {
        super(repositorio, Tecnico.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tecnico> crear(Tecnico datos) {
        return super.crear(datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Tecnico actualizar(Long id, Tecnico datos) {
        return super.actualizar(id, datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(Long id) {
        return super.eliminar(id);
    }
}
