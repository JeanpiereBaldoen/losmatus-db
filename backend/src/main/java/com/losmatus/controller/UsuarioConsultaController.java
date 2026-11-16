package com.losmatus.controller;

import com.losmatus.config.SecurityUtils;
import com.losmatus.dto.ActualizarUsuarioRequest;
import com.losmatus.dto.CambiarPasswordRequest;
import com.losmatus.dto.UsuarioDTO;
import com.losmatus.dto.UsuarioRequest;
import com.losmatus.entity.Usuario;
import com.losmatus.repository.UsuarioRepository;
import com.losmatus.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioConsultaController {

    private final UsuarioRepository repositorio;
    private final UsuarioService usuarioService;

    public UsuarioConsultaController(UsuarioRepository repositorio, UsuarioService usuarioService) {
        this.repositorio = repositorio;
        this.usuarioService = usuarioService;
    }

    // No se devuelve el campo password, ni siquiera su hash.
    public record UsuarioResumen(Long idUsuario, String email, Boolean activo,
                                 Long idRol, LocalDateTime fechaCreacion) {
        static UsuarioResumen desde(Usuario u) {
            return new UsuarioResumen(u.getIdUsuario(), u.getEmail(),
                    u.getActivo(), u.getIdRol(), u.getFechaCreacion());
        }
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<UsuarioResumen> listar() {
        return repositorio.findAll().stream().map(UsuarioResumen::desde).toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public UsuarioResumen obtener(@PathVariable Long id) {
        return repositorio.findById(id).map(UsuarioResumen::desde).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con ID: " + id));
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> obtenerPerfil() {
        Long idUsuarioActual = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(usuarioService.buscarPorId(idUsuarioActual));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody ActualizarUsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, request));
    }

    @PutMapping("/cambiar-password")
    public ResponseEntity<Void> cambiarPassword(@Valid @RequestBody CambiarPasswordRequest request) {
        Long idUsuarioActual = SecurityUtils.getCurrentUserId();
        usuarioService.cambiarPassword(idUsuarioActual, request);
        return ResponseEntity.noContent().build();
    }
}
