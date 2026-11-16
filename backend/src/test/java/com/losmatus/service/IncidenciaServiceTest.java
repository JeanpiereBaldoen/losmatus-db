package com.losmatus.service;

import com.losmatus.config.CustomUserDetails;
import com.losmatus.dto.IncidenciaRequest;
import com.losmatus.entity.HistorialIncidencia;
import com.losmatus.entity.Incidencia;
import com.losmatus.exception.ForbiddenException;
import com.losmatus.repository.ConfiguracionSlaRepository;
import com.losmatus.repository.HistorialIncidenciaRepository;
import com.losmatus.repository.IncidenciaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidenciaServiceTest {

    @Mock
    private IncidenciaRepository incidenciaRepository;

    @Mock
    private HistorialIncidenciaRepository historialIncidenciaRepository;

    @Mock
    private ConfiguracionSlaRepository configuracionSlaRepository;

    @InjectMocks
    private IncidenciaService incidenciaService;

    @BeforeEach
    void setUpSecurity() {
        CustomUserDetails userDetails = new CustomUserDetails(
                5L,
                "cliente@test.com",
                "pass",
                true,
                "CLIENTE",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void clearSecurity() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testListarTodasComoClienteSoloRetornaSusIncidencias() {
        Incidencia miIncidencia = new Incidencia();
        miIncidencia.setIdIncidencia(1L);
        miIncidencia.setIdUsuarioReporta(5L);

        when(incidenciaRepository.findByIdUsuarioReporta(5L)).thenReturn(List.of(miIncidencia));

        List<Incidencia> resultado = incidenciaService.listarTodas();

        assertEquals(1, resultado.size());
        assertEquals(5L, resultado.get(0).getIdUsuarioReporta());
        verify(incidenciaRepository, times(1)).findByIdUsuarioReporta(5L);
        verify(incidenciaRepository, never()).findAll();
    }

    @Test
    void testBuscarPorIdDeOtroUsuarioLanzaForbiddenParaCliente() {
        Incidencia otraIncidencia = new Incidencia();
        otraIncidencia.setIdIncidencia(2L);
        otraIncidencia.setIdUsuarioReporta(99L); // ID de otro usuario

        when(incidenciaRepository.findById(2L)).thenReturn(Optional.of(otraIncidencia));

        assertThrows(ForbiddenException.class, () -> incidenciaService.buscarPorId(2L));
    }

    @Test
    void testCrearIncidenciaComoClienteAsignaSuPropioId() {
        IncidenciaRequest request = new IncidenciaRequest(
                "INC-001",
                "Falla en la red",
                1L,
                1L,
                1L,
                999L // Intenta reportar con otro ID
        );

        when(configuracionSlaRepository.findByIdUsuarioAndIdSeveridad(eq(5L), any())).thenReturn(Optional.empty());
        when(incidenciaRepository.save(any(Incidencia.class))).thenAnswer(i -> {
            Incidencia inc = i.getArgument(0);
            inc.setIdIncidencia(10L);
            return inc;
        });

        Incidencia guardada = incidenciaService.crear(request);

        assertNotNull(guardada);
        assertEquals(5L, guardada.getIdUsuarioReporta()); // Forzado al usuario autenticado (5L)
        verify(historialIncidenciaRepository, times(1)).save(any(HistorialIncidencia.class));
    }
}

