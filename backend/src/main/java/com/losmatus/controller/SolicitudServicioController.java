package com.losmatus.controller;

import com.losmatus.dto.SolicitudServicioRequest;
import com.losmatus.entity.SolicitudServicio;
import com.losmatus.service.SolicitudServicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes-servicio")
public class SolicitudServicioController {

    private final SolicitudServicioService service;

    public SolicitudServicioController(SolicitudServicioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SolicitudServicio>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudServicio> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SolicitudServicio> crear(@Valid @RequestBody SolicitudServicioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudServicio> actualizar(@PathVariable Long id, @Valid @RequestBody SolicitudServicioRequest request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado/{idEstado}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public ResponseEntity<SolicitudServicio> cambiarEstado(
            @PathVariable Long id,
            @PathVariable Long idEstado,
            @RequestParam(required = false) String detalle
    ) {
        return ResponseEntity.ok(service.cambiarEstado(id, idEstado, detalle));
    }
}
