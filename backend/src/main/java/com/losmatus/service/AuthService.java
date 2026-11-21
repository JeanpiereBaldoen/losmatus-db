package com.losmatus.service;

import com.losmatus.config.CustomUserDetails;
import com.losmatus.dto.LoginRequest;
import com.losmatus.dto.LoginResponse;
import com.losmatus.dto.RegistroUsuarioRequest;
import com.losmatus.entity.Persona;
import com.losmatus.entity.Rol;
import com.losmatus.entity.Usuario;
import com.losmatus.exception.BadRequestException;
import com.losmatus.exception.UnauthorizedException;
import com.losmatus.repository.PersonaRepository;
import com.losmatus.repository.RolRepository;
import com.losmatus.repository.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PersonaRepository personaRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Credenciales incorrectas"));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new UnauthorizedException("La cuenta de usuario se encuentra inactiva");
        }

        boolean passwordValido = false;
        String storedPassword = usuario.getPassword();
        if (storedPassword != null) {
            passwordValido = passwordEncoder.matches(request.password(), storedPassword);
        }

        if (!passwordValido) {
            throw new BadCredentialsException("Credenciales incorrectas");
        }

        String rolNombre = rolRepository.findById(usuario.getIdRol())
                .map(Rol::getNombre)
                .orElse("CLIENTE");

        String authorityName = rolNombre.startsWith("ROLE_") ? rolNombre : "ROLE_" + rolNombre.toUpperCase();
        CustomUserDetails userDetails = new CustomUserDetails(
                usuario.getIdUsuario(),
                usuario.getEmail(),
                usuario.getPassword(),
                true,
                rolNombre,
                Collections.singletonList(new SimpleGrantedAuthority(authorityName))
        );

        String token = jwtService.generateToken(userDetails, usuario.getIdUsuario(), rolNombre);
        return new LoginResponse(token, usuario.getIdUsuario(), usuario.getEmail(), rolNombre);
    }

    @Transactional
    public LoginResponse registroCliente(RegistroUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BadRequestException("El email ya se encuentra registrado: " + request.email());
        }

        if (personaRepository.existsByDni(request.dni())) {
            throw new BadRequestException("El DNI ya se encuentra registrado: " + request.dni());
        }

        // El registro publico asigna estrictamente el rol CLIENTE
        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new BadRequestException("No se encontro el rol CLIENTE en el sistema"));

        Usuario usuario = new Usuario();
        usuario.setEmail(request.email().trim().toLowerCase());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setActivo(true);
        usuario.setIdRol(rolCliente.getIdRol());
        usuario.setFechaCreacion(LocalDateTime.now());
        Usuario guardado = usuarioRepository.save(usuario);

        Persona persona = new Persona();
        persona.setUsuarioId(guardado.getIdUsuario());
        persona.setDni(request.dni().trim());
        persona.setNombrePersona(request.nombre().trim());
        persona.setApePaternoPersona(request.apellidoPaterno() != null ? request.apellidoPaterno().trim() : null);
        persona.setApeMaternoPersona(request.apellidoMaterno() != null ? request.apellidoMaterno().trim() : null);
        persona.setTelefono(request.telefono() != null ? request.telefono().trim() : null);
        persona.setDireccion(request.direccion() != null ? request.direccion().trim() : null);
        personaRepository.save(persona);

        String authorityName = "ROLE_" + rolCliente.getNombre().toUpperCase();
        CustomUserDetails userDetails = new CustomUserDetails(
                guardado.getIdUsuario(),
                guardado.getEmail(),
                guardado.getPassword(),
                true,
                rolCliente.getNombre(),
                Collections.singletonList(new SimpleGrantedAuthority(authorityName))
        );

        String token = jwtService.generateToken(userDetails, guardado.getIdUsuario(), rolCliente.getNombre());
        return new LoginResponse(token, guardado.getIdUsuario(), guardado.getEmail(), rolCliente.getNombre());
    }
}

