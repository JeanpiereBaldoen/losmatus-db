package com.losmatus.controller;

import com.losmatus.config.SecurityUtils;
import com.losmatus.dto.PersonaDTO;
import com.losmatus.dto.PersonaRequest;
import com.losmatus.service.PersonaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public ResponseEntity<List<PersonaDTO>> listar() {
        return ResponseEntity.ok(personaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(personaService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<PersonaDTO> obtenerPorUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(personaService.buscarPorUsuarioId(usuarioId));
    }

    @GetMapping("/mi-perfil")
    public ResponseEntity<PersonaDTO> obtenerMiPerfil() {
        Long idUsuario = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(personaService.buscarPorUsuarioId(idUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PersonaRequest request) {
        return ResponseEntity.ok(personaService.actualizar(id, request));
    }
}

