package com.losmatus.controller;

import com.losmatus.config.SecurityUtils;
import com.losmatus.entity.Suscripcion;
import com.losmatus.exception.BadRequestException;
import com.losmatus.exception.ForbiddenException;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.ModuloRepository;
import com.losmatus.repository.SuscripcionRepository;
import com.losmatus.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {

    private final SuscripcionRepository repo;
    private final UsuarioRepository usuarioRepo;
    private final ModuloRepository moduloRepo;

    public SuscripcionController(
            SuscripcionRepository repo,
            UsuarioRepository usuarioRepo,
            ModuloRepository moduloRepo
    ) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
        this.moduloRepo = moduloRepo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<Suscripcion>> listar() {
        if (SecurityUtils.isCliente()) {
            return ResponseEntity.ok(repo.findByUsuarioId(SecurityUtils.getCurrentUserId()));
        }
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/mis-suscripciones")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Suscripcion>> misSuscripciones() {
        return ResponseEntity.ok(repo.findByUsuarioId(SecurityUtils.getCurrentUserId()));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Suscripcion> obtenerPorId(@PathVariable Long id) {
        Suscripcion sus = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suscripcion no encontrada con ID: " + id));

        if (SecurityUtils.isCliente() && !sus.getUsuarioId().equals(SecurityUtils.getCurrentUserId())) {
            throw new ForbiddenException("No tiene permisos para ver esta suscripcion");
        }

        return ResponseEntity.ok(sus);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Suscripcion> crear(@RequestBody Suscripcion datos) {
        if (!usuarioRepo.existsById(datos.getUsuarioId())) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + datos.getUsuarioId());
        }
        if (!moduloRepo.existsById(datos.getModuloId())) {
            throw new ResourceNotFoundException("Modulo no encontrado con ID: " + datos.getModuloId());
        }

        if (repo.findByUsuarioIdAndModuloId(datos.getUsuarioId(), datos.getModuloId()).isPresent()) {
            throw new BadRequestException("El usuario ya cuenta con una suscripcion para este modulo");
        }

        Suscripcion nueva = new Suscripcion();
        nueva.setUsuarioId(datos.getUsuarioId());
        nueva.setModuloId(datos.getModuloId());
        nueva.setActivo(datos.getActivo() != null ? datos.getActivo() : true);
        nueva.setFechaInicio(datos.getFechaInicio() != null ? datos.getFechaInicio() : LocalDateTime.now());
        nueva.setFechaFin(datos.getFechaFin());

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(nueva));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Suscripcion> actualizar(@PathVariable Long id, @RequestBody Suscripcion datos) {
        Suscripcion existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suscripcion no encontrada con ID: " + id));

        if (datos.getActivo() != null) existente.setActivo(datos.getActivo());
        if (datos.getFechaInicio() != null) existente.setFechaInicio(datos.getFechaInicio());
        if (datos.getFechaFin() != null) existente.setFechaFin(datos.getFechaFin());

        return ResponseEntity.ok(repo.save(existente));
    }
}

