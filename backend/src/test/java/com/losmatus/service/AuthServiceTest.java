package com.losmatus.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private Usuario usuarioDemo;
    private Rol rolCliente;

    @BeforeEach
    void setUp() {
        rolCliente = new Rol();
        rolCliente.setIdRol(3L);
        rolCliente.setNombre("CLIENTE");

        usuarioDemo = new Usuario();
        usuarioDemo.setIdUsuario(100L);
        usuarioDemo.setEmail("cliente@losmatus.com");
        usuarioDemo.setPassword("$2a$10$hashedPassword");
        usuarioDemo.setActivo(true);
        usuarioDemo.setIdRol(3L);
    }

    @Test
    void testLoginExitoso() {
        LoginRequest request = new LoginRequest("cliente@losmatus.com", "password123");

        when(usuarioRepository.findByEmail("cliente@losmatus.com")).thenReturn(Optional.of(usuarioDemo));
        when(passwordEncoder.matches("password123", "$2a$10$hashedPassword")).thenReturn(true);
        when(rolRepository.findById(3L)).thenReturn(Optional.of(rolCliente));
        when(jwtService.generateToken(any(), eq(100L), eq("CLIENTE"))).thenReturn("fake-jwt-token");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.token());
        assertEquals(100L, response.idUsuario());
        assertEquals("cliente@losmatus.com", response.email());
        assertEquals("CLIENTE", response.rol());
    }

    @Test
    void testLoginCompatibilidadPasswordLegacySinSobrescribir() {
        Usuario usuarioLegacy = new Usuario();
        usuarioLegacy.setIdUsuario(101L);
        usuarioLegacy.setEmail("legacy@losmatus.com");
        usuarioLegacy.setPassword("CUENTA_DEMOSTRACION_SIN_LOGIN");
        usuarioLegacy.setActivo(true);
        usuarioLegacy.setIdRol(3L);

        LoginRequest request = new LoginRequest("legacy@losmatus.com", "CUENTA_DEMOSTRACION_SIN_LOGIN");

        when(usuarioRepository.findByEmail("legacy@losmatus.com")).thenReturn(Optional.of(usuarioLegacy));
        when(rolRepository.findById(3L)).thenReturn(Optional.of(rolCliente));
        when(jwtService.generateToken(any(), eq(101L), eq("CLIENTE"))).thenReturn("token-legacy");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("token-legacy", response.token());
        // Se valida que no se sobrescriba automaticamente la base de datos
        verify(usuarioRepository, never()).save(any());
        verify(passwordEncoder, never()).matches(any(), any());
    }

    @Test
    void testLoginPasswordInvalido() {
        LoginRequest request = new LoginRequest("cliente@losmatus.com", "wrongpassword");

        when(usuarioRepository.findByEmail("cliente@losmatus.com")).thenReturn(Optional.of(usuarioDemo));
        when(passwordEncoder.matches("wrongpassword", "$2a$10$hashedPassword")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
    }

    @Test
    void testLoginUsuarioInactivo() {
        usuarioDemo.setActivo(false);
        LoginRequest request = new LoginRequest("cliente@losmatus.com", "password123");

        when(usuarioRepository.findByEmail("cliente@losmatus.com")).thenReturn(Optional.of(usuarioDemo));

        assertThrows(UnauthorizedException.class, () -> authService.login(request));
    }

    @Test
    void testRegistroClienteExitoso() {
        RegistroUsuarioRequest request = new RegistroUsuarioRequest(
                "nuevo@losmatus.com",
                "password123",
                "12345678",
                "Carlos",
                "Perez",
                "Gomez",
                "987654321",
                "Av. Principal 123"
        );

        when(usuarioRepository.existsByEmail("nuevo@losmatus.com")).thenReturn(false);
        when(personaRepository.existsByDni("12345678")).thenReturn(false);
        when(rolRepository.findByNombre("CLIENTE")).thenReturn(Optional.of(rolCliente));
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$encoded");

        Usuario savedUser = new Usuario();
        savedUser.setIdUsuario(200L);
        savedUser.setEmail("nuevo@losmatus.com");
        savedUser.setPassword("$2a$10$encoded");
        savedUser.setActivo(true);
        savedUser.setIdRol(3L);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(savedUser);
        when(personaRepository.save(any(Persona.class))).thenAnswer(i -> i.getArgument(0));
        when(jwtService.generateToken(any(), eq(200L), eq("CLIENTE"))).thenReturn("new-jwt-token");

        LoginResponse response = authService.registroCliente(request);

        assertNotNull(response);
        assertEquals("new-jwt-token", response.token());
        assertEquals(200L, response.idUsuario());
        assertEquals("nuevo@losmatus.com", response.email());
        assertEquals("CLIENTE", response.rol());
        verify(personaRepository, times(1)).save(any(Persona.class));
    }

    @Test
    void testRegistroFallaSiRolClienteNoExisteSinIdFijo() {
        RegistroUsuarioRequest request = new RegistroUsuarioRequest(
                "nuevo@losmatus.com", "password123", "12345678", "Carlos", null, null, null, null
        );

        when(usuarioRepository.existsByEmail("nuevo@losmatus.com")).thenReturn(false);
        when(personaRepository.existsByDni("12345678")).thenReturn(false);
        when(rolRepository.findByNombre("CLIENTE")).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class, () -> authService.registroCliente(request));
        assertTrue(ex.getMessage().contains("No se encontro el rol CLIENTE en el sistema"));

        // Se verifica que NUNCA busque por ID 3 fijo
        verify(rolRepository, never()).findById(3L);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testRegistroEmailDuplicado() {
        RegistroUsuarioRequest request = new RegistroUsuarioRequest(
                "duplicado@losmatus.com", "password123", "12345678", "Carlos", null, null, null, null
        );

        when(usuarioRepository.existsByEmail("duplicado@losmatus.com")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> authService.registroCliente(request));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testRegistroDniDuplicado() {
        RegistroUsuarioRequest request = new RegistroUsuarioRequest(
                "nuevo@losmatus.com", "password123", "87654321", "Carlos", null, null, null, null
        );

        when(usuarioRepository.existsByEmail("nuevo@losmatus.com")).thenReturn(false);
        when(personaRepository.existsByDni("87654321")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> authService.registroCliente(request));
        verify(usuarioRepository, never()).save(any());
    }
}
