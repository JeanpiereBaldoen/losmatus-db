package com.losmatus.controller;

import com.losmatus.entity.AuditoriaInforme;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.AuditoriaInformeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria-informes")
@PreAuthorize("hasRole('ADMIN')")
public class AuditoriaInformeController {

    private final AuditoriaInformeRepository repo;

    public AuditoriaInformeController(AuditoriaInformeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<AuditoriaInforme>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<AuditoriaInforme> obtenerPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de auditoria no encontrado con ID: " + id));
    }

    @GetMapping("/informe/{idInforme}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<AuditoriaInforme>> listarPorInforme(@PathVariable Long idInforme) {
        return ResponseEntity.ok(repo.findByIdInformeOrderByFechaCambioDesc(idInforme));
    }
}

