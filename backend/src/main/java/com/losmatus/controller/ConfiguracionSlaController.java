package com.losmatus.controller;

import com.losmatus.entity.ConfiguracionSla;
import com.losmatus.exception.BadRequestException;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.ConfiguracionSlaRepository;
import com.losmatus.repository.SeveridadRepository;
import com.losmatus.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuracion-sla")
@PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
public class ConfiguracionSlaController {

    private final ConfiguracionSlaRepository repo;
    private final UsuarioRepository usuarioRepo;
    private final SeveridadRepository severidadRepo;

    public ConfiguracionSlaController(
            ConfiguracionSlaRepository repo,
            UsuarioRepository usuarioRepo,
            SeveridadRepository severidadRepo
    ) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
        this.severidadRepo = severidadRepo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ConfiguracionSla>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/usuario/{idUsuario}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<ConfiguracionSla>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(repo.findByIdUsuario(idUsuario));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<ConfiguracionSla> crearOActualizar(@RequestBody ConfiguracionSla datos) {
        if (!usuarioRepo.existsById(datos.getIdUsuario())) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + datos.getIdUsuario());
        }
        if (!severidadRepo.existsById(datos.getIdSeveridad())) {
            throw new ResourceNotFoundException("Severidad no encontrada con ID: " + datos.getIdSeveridad());
        }
        if (datos.getTiempoResolucionHoras() == null || datos.getTiempoResolucionHoras() <= 0) {
            throw new BadRequestException("El tiempo de resolucion en horas debe ser mayor a 0");
        }

        ConfiguracionSla sla = repo.findByIdUsuarioAndIdSeveridad(datos.getIdUsuario(), datos.getIdSeveridad())
                .orElse(new ConfiguracionSla());

        sla.setIdUsuario(datos.getIdUsuario());
        sla.setIdSeveridad(datos.getIdSeveridad());
        sla.setTiempoResolucionHoras(datos.getTiempoResolucionHoras());
        sla.setTiempoRespuestaHoras(datos.getTiempoRespuestaHoras());

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(sla));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Configuracion SLA no encontrada con ID: " + id);
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

