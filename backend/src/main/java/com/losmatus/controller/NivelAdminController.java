package com.losmatus.controller;

import com.losmatus.entity.NivelAdmin;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.NivelAdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/niveles-admin")
@PreAuthorize("hasRole('ADMIN')")
public class NivelAdminController {

    private final NivelAdminRepository repo;

    public NivelAdminController(NivelAdminRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<NivelAdmin>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<NivelAdmin> obtenerPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Nivel de admin no encontrado con ID: " + id));
    }
}

