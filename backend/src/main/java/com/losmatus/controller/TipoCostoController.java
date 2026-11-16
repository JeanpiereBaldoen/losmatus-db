package com.losmatus.controller;

import com.losmatus.entity.TipoCosto;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.TipoCostoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-costo")
public class TipoCostoController {

    private final TipoCostoRepository repo;

    public TipoCostoController(TipoCostoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<TipoCosto>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<TipoCosto> obtenerPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de costo no encontrado con ID: " + id));
    }
}

