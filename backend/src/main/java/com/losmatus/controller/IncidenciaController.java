package com.losmatus.controller;

import com.losmatus.dto.IncidenciaRequest;
import com.losmatus.entity.Incidencia;
import com.losmatus.service.IncidenciaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    // Constructor
    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @GetMapping
    public ResponseEntity<List<Incidencia>> listar() {
        return ResponseEntity.ok(incidenciaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(incidenciaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Incidencia> crear(
            @Valid @RequestBody IncidenciaRequest datos) {

        Incidencia nueva = incidenciaService.crear(datos);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Incidencia> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody IncidenciaRequest datos) {

        Incidencia actualizada = incidenciaService.actualizar(id, datos);

        return ResponseEntity.ok(actualizada);
    }
}