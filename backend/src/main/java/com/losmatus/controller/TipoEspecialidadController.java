package com.losmatus.controller;

import com.losmatus.entity.TipoEspecialidad;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.TipoEspecialidadRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-especialidad")
public class TipoEspecialidadController {

    private final TipoEspecialidadRepository repo;

    public TipoEspecialidadController(TipoEspecialidadRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<TipoEspecialidad>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<TipoEspecialidad> obtenerPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de especialidad no encontrado con ID: " + id));
    }
}

