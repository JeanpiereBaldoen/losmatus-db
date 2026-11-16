package com.losmatus.controller;

import com.losmatus.entity.EstadoTecnico;
import com.losmatus.repository.EstadoTecnicoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estados-tecnico")
public class EstadoTecnicoController extends CrudBaseController<EstadoTecnico> {

    public EstadoTecnicoController(EstadoTecnicoRepository repositorio) {
        super(repositorio, EstadoTecnico.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstadoTecnico> crear(EstadoTecnico datos) {
        return super.crear(datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public EstadoTecnico actualizar(Long id, EstadoTecnico datos) {
        return super.actualizar(id, datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(Long id) {
        return super.eliminar(id);
    }
}
