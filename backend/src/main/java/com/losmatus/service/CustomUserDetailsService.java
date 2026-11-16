package com.losmatus.service;

import com.losmatus.config.CustomUserDetails;
import com.losmatus.entity.Rol;
import com.losmatus.entity.Usuario;
import com.losmatus.repository.RolRepository;
import com.losmatus.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        String rolNombre = rolRepository.findById(usuario.getIdRol())
                .map(Rol::getNombre)
                .orElse("CLIENTE");

        // Format role for Spring Security (e.g. ROLE_ADMIN, ROLE_TECNICO, ROLE_CLIENTE)
        String authorityName = rolNombre.startsWith("ROLE_") ? rolNombre : "ROLE_" + rolNombre.toUpperCase();

        return new CustomUserDetails(
                usuario.getIdUsuario(),
                usuario.getEmail(),
                usuario.getPassword(),
                Boolean.TRUE.equals(usuario.getActivo()),
                rolNombre,
                Collections.singletonList(new SimpleGrantedAuthority(authorityName))
        );
    }
}

