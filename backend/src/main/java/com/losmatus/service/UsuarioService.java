package com.losmatus.service;

import com.losmatus.dto.ActualizarUsuarioRequest;
import com.losmatus.dto.CambiarPasswordRequest;
import com.losmatus.dto.UsuarioDTO;
import com.losmatus.dto.UsuarioRequest;
import com.losmatus.entity.Rol;
import com.losmatus.entity.Usuario;
import com.losmatus.exception.BadRequestException;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.RolRepository;
import com.losmatus.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public UsuarioDTO crearUsuario(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BadRequestException("El email ya se encuentra registrado: " + request.email());
        }

        if (!rolRepository.existsById(request.idRol())) {
            throw new BadRequestException("El rol con ID " + request.idRol() + " no existe");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.email().trim().toLowerCase());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setIdRol(request.idRol());
        usuario.setActivo(request.activo() != null ? request.activo() : true);
        usuario.setFechaCreacion(LocalDateTime.now());

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADTO(guardado);
    }

    @Transactional
    public UsuarioDTO actualizarUsuario(Long id, ActualizarUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        if (request.email() != null && !request.email().equalsIgnoreCase(usuario.getEmail())) {
            if (usuarioRepository.existsByEmail(request.email())) {
                throw new BadRequestException("El email ya se encuentra en uso: " + request.email());
            }
            usuario.setEmail(request.email().trim().toLowerCase());
        }

        if (request.idRol() != null) {
            if (!rolRepository.existsById(request.idRol())) {
                throw new BadRequestException("El rol con ID " + request.idRol() + " no existe");
            }
            usuario.setIdRol(request.idRol());
        }

        if (request.activo() != null) {
            usuario.setActivo(request.activo());
        }

        return convertirADTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public void cambiarPassword(Long idUsuario, CambiarPasswordRequest request) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        if (!passwordEncoder.matches(request.passwordActual(), usuario.getPassword())) {
            throw new BadRequestException("La contrasena actual es incorrecta");
        }

        usuario.setPassword(passwordEncoder.encode(request.passwordNuevo()));
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO convertirADTO(Usuario u) {
        String rolNombre = rolRepository.findById(u.getIdRol())
                .map(Rol::getNombre)
                .orElse("DESCONOCIDO");

        return new UsuarioDTO(
                u.getIdUsuario(),
                u.getEmail(),
                u.getActivo(),
                u.getIdRol(),
                rolNombre,
                u.getFechaCreacion()
        );
    }
}

