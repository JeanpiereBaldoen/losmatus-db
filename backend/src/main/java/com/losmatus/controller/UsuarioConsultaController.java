package com.losmatus.controller;

import com.losmatus.entity.Usuario;
import com.losmatus.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioConsultaController {

    private final UsuarioRepository repositorio;

    public UsuarioConsultaController(UsuarioRepository repositorio) {
        this.repositorio = repositorio;
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
}
