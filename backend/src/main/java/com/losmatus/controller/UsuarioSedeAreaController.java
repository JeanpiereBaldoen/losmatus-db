package com.losmatus.controller;

import com.losmatus.entity.UsuarioSedeArea;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.AreaRepository;
import com.losmatus.repository.SedeRepository;
import com.losmatus.repository.UsuarioRepository;
import com.losmatus.repository.UsuarioSedeAreaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario-sede-area")
public class UsuarioSedeAreaController {

    private final UsuarioSedeAreaRepository repo;
    private final UsuarioRepository usuarioRepo;
    private final SedeRepository sedeRepo;
    private final AreaRepository areaRepo;

    public UsuarioSedeAreaController(
            UsuarioSedeAreaRepository repo,
            UsuarioRepository usuarioRepo,
            SedeRepository sedeRepo,
            AreaRepository areaRepo
    ) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
        this.sedeRepo = sedeRepo;
        this.areaRepo = areaRepo;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UsuarioSedeArea>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/usuario/{idUsuario}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UsuarioSedeArea>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(repo.findByIdUsuario(idUsuario));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<UsuarioSedeArea> asignar(@RequestBody UsuarioSedeArea datos) {
        if (!usuarioRepo.existsById(datos.getIdUsuario())) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + datos.getIdUsuario());
        }
        if (!sedeRepo.existsById(datos.getIdSede())) {
            throw new ResourceNotFoundException("Sede no encontrada con ID: " + datos.getIdSede());
        }
        if (!areaRepo.existsById(datos.getIdArea())) {
            throw new ResourceNotFoundException("Area no encontrada con ID: " + datos.getIdArea());
        }

        UsuarioSedeArea nuevo = new UsuarioSedeArea();
        nuevo.setIdUsuario(datos.getIdUsuario());
        nuevo.setIdSede(datos.getIdSede());
        nuevo.setIdArea(datos.getIdArea());

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(nuevo));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Asignacion no encontrada con ID: " + id);
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

