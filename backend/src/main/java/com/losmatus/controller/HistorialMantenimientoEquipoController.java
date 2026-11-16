package com.losmatus.controller;

import com.losmatus.entity.HistorialMantenimientoEquipo;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.EquipoRepository;
import com.losmatus.repository.HistorialMantenimientoEquipoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/equipos/{idEquipo}/historial-mantenimiento")
public class HistorialMantenimientoEquipoController {

    private final HistorialMantenimientoEquipoRepository repo;
    private final EquipoRepository equipoRepo;

    public HistorialMantenimientoEquipoController(
            HistorialMantenimientoEquipoRepository repo,
            EquipoRepository equipoRepo
    ) {
        this.repo = repo;
        this.equipoRepo = equipoRepo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<HistorialMantenimientoEquipo>> listarPorEquipo(@PathVariable Long idEquipo) {
        if (!equipoRepo.existsById(idEquipo)) {
            throw new ResourceNotFoundException("Equipo no encontrado con ID: " + idEquipo);
        }
        return ResponseEntity.ok(repo.findByIdEquipo(idEquipo));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    @Transactional
    public ResponseEntity<HistorialMantenimientoEquipo> registrarEvento(
            @PathVariable Long idEquipo,
            @RequestBody HistorialMantenimientoEquipo datos
    ) {
        if (!equipoRepo.existsById(idEquipo)) {
            throw new ResourceNotFoundException("Equipo no encontrado con ID: " + idEquipo);
        }

        HistorialMantenimientoEquipo hist = new HistorialMantenimientoEquipo();
        hist.setIdEquipo(idEquipo);
        hist.setDescripcion(datos.getDescripcion());
        hist.setTipoEvento(datos.getTipoEvento());
        hist.setTecnicoResponsable(datos.getTecnicoResponsable());
        hist.setIdInforme(datos.getIdInforme());
        hist.setFechaEvento(datos.getFechaEvento() != null ? datos.getFechaEvento() : LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(hist));
    }
}

