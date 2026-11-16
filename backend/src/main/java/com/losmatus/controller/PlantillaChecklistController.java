package com.losmatus.controller;

import com.losmatus.entity.PlantillaChecklist;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.PlantillaChecklistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantillas-checklist")
public class PlantillaChecklistController {

    private final PlantillaChecklistRepository repo;

    public PlantillaChecklistController(PlantillaChecklistRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<PlantillaChecklist>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<PlantillaChecklist> obtenerPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Plantilla no encontrada con ID: " + id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<PlantillaChecklist> crear(@RequestBody PlantillaChecklist datos) {
        PlantillaChecklist nueva = new PlantillaChecklist();
        nueva.setNombre(datos.getNombre());
        nueva.setTipoEquipo(datos.getTipoEquipo());
        nueva.setEstructura(datos.getEstructura());
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(nueva));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<PlantillaChecklist> actualizar(@PathVariable Long id, @RequestBody PlantillaChecklist datos) {
        PlantillaChecklist existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plantilla no encontrada con ID: " + id));

        if (datos.getNombre() != null) existente.setNombre(datos.getNombre());
        if (datos.getTipoEquipo() != null) existente.setTipoEquipo(datos.getTipoEquipo());
        if (datos.getEstructura() != null) existente.setEstructura(datos.getEstructura());

        return ResponseEntity.ok(repo.save(existente));
    }
}

