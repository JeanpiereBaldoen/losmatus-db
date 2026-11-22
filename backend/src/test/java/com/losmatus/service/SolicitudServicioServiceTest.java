package com.losmatus.service;

import com.losmatus.config.CustomUserDetails;
import com.losmatus.dto.SolicitudServicioRequest;
import com.losmatus.entity.HistorialSolicitudServicio;
import com.losmatus.entity.SolicitudServicio;
import com.losmatus.exception.ForbiddenException;
import com.losmatus.repository.HistorialSolicitudServicioRepository;
import com.losmatus.repository.SolicitudServicioRepository;
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

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServicioServiceTest {

    @Mock
    private SolicitudServicioRepository repo;

    @Mock
    private HistorialSolicitudServicioRepository historialRepo;

    @Mock
    private com.losmatus.repository.UsuarioRepository usuarioRepo;

    @InjectMocks
    private SolicitudServicioService service;

    @BeforeEach
    void setUpSecurity() {
        CustomUserDetails userDetails = new CustomUserDetails(
                7L,
                "cliente7@test.com",
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
    void testListarTodasComoClienteSoloRetornaSusSolicitudes() {
        SolicitudServicio miSolicitud = new SolicitudServicio();
        miSolicitud.setIdSolicitud(1L);
        miSolicitud.setIdUsuario(7L);

        when(repo.findByIdUsuario(7L)).thenReturn(List.of(miSolicitud));

        List<SolicitudServicio> resultado = service.listarTodas();

        assertEquals(1, resultado.size());
        assertEquals(7L, resultado.get(0).getIdUsuario());
        verify(repo, times(1)).findByIdUsuario(7L);
        verify(repo, never()).findAll();
    }

    @Test
    void testBuscarPorIdDeOtroUsuarioLanzaForbiddenParaCliente() {
        SolicitudServicio otraSolicitud = new SolicitudServicio();
        otraSolicitud.setIdSolicitud(2L);
        otraSolicitud.setIdUsuario(88L);

        when(repo.findById(2L)).thenReturn(Optional.of(otraSolicitud));

        assertThrows(ForbiddenException.class, () -> service.buscarPorId(2L));
    }

    @Test
    void testCrearGeneraCodigoYCorrelativoAutomatico() {
        int anio = Year.now().getValue();
        SolicitudServicioRequest request = new SolicitudServicioRequest(
                "Mantenimiento preventivo",
                1L,
                2L,
                1L,
                false,
                null
        );

        when(repo.findMaxCorrelativoByAnio(anio)).thenReturn(Optional.of(15));
        when(repo.save(any(SolicitudServicio.class))).thenAnswer(i -> {
            SolicitudServicio s = i.getArgument(0);
            s.setIdSolicitud(50L);
            return s;
        });

        SolicitudServicio guardada = service.crear(request);

        assertNotNull(guardada);
        assertEquals(16, guardada.getCorrelativo());
        assertEquals(anio, guardada.getAnio());
        assertEquals(String.format("SOL-%d-0016", anio), guardada.getCodigo());
        assertEquals(7L, guardada.getIdUsuario()); // Asignado al cliente autenticado
        verify(historialRepo, times(1)).save(any(HistorialSolicitudServicio.class));
    }

    @Test
    void testCrearContactoPublicoExitoso() {
        int anio = Year.now().getValue();
        com.losmatus.dto.ContactoPublicoRequest request = new com.losmatus.dto.ContactoPublicoRequest(
                "Carlos Mendoza",
                "carlos@innovatech.pe",
                "Innova Tech SAC",
                "999888777",
                "Cableado Estructurado",
                "Requerimos cotización de 40 puntos de red"
        );

        when(repo.findMaxCorrelativoByAnio(anio)).thenReturn(Optional.of(3));
        when(usuarioRepo.findByEmail("carlos@innovatech.pe")).thenReturn(Optional.empty());
        when(repo.save(any(SolicitudServicio.class))).thenAnswer(i -> {
            SolicitudServicio s = i.getArgument(0);
            s.setIdSolicitud(99L);
            return s;
        });

        SolicitudServicio guardada = service.crearContactoPublico(request);

        assertNotNull(guardada);
        assertEquals(4, guardada.getCorrelativo());
        assertEquals(String.format("SOL-%d-0004", anio), guardada.getCodigo());
        assertEquals(1L, guardada.getIdUsuario()); // Usuario default admin
        assertTrue(guardada.getRequiereCotizacion());
        assertTrue(guardada.getDescripcion().contains("Carlos Mendoza"));
        assertTrue(guardada.getDescripcion().contains("Innova Tech SAC"));
        verify(historialRepo, times(1)).save(any(HistorialSolicitudServicio.class));
    }
}

