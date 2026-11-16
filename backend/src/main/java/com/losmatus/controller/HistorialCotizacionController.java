package com.losmatus.controller;

import com.losmatus.config.SecurityUtils;
import com.losmatus.entity.HistorialCotizacion;
import com.losmatus.repository.HistorialCotizacionRepository;
import com.losmatus.service.SolicitudServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes-servicio/{idSolicitud}/cotizaciones")
public class HistorialCotizacionController {

    private final HistorialCotizacionRepository repo;
    private final SolicitudServicioService service;

    public HistorialCotizacionController(
            HistorialCotizacionRepository repo,
            SolicitudServicioService service
    ) {
        this.repo = repo;
        this.service = service;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<HistorialCotizacion>> listarPorSolicitud(@PathVariable Long idSolicitud) {
        service.buscarPorId(idSolicitud); // Valida acceso
        return ResponseEntity.ok(repo.findByIdSolicitudOrderByVersionDesc(idSolicitud));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    @Transactional
    public ResponseEntity<HistorialCotizacion> generarCotizacion(
            @PathVariable Long idSolicitud,
            @RequestBody HistorialCotizacion datos
    ) {
        service.buscarPorId(idSolicitud);

        List<HistorialCotizacion> anteriores = repo.findByIdSolicitudOrderByVersionDesc(idSolicitud);
        int nuevaVersion = anteriores.isEmpty() ? 1 : (anteriores.get(0).getVersion() != null ? anteriores.get(0).getVersion() + 1 : 1);

        HistorialCotizacion cotizacion = new HistorialCotizacion();
        cotizacion.setIdSolicitud(idSolicitud);
        cotizacion.setIdUsuario(SecurityUtils.getCurrentUserId());
        cotizacion.setVersion(nuevaVersion);
        cotizacion.setFechaGeneracion(LocalDateTime.now());
        cotizacion.setMontoTotalHistorico(datos.getMontoTotalHistorico());
        cotizacion.setDetallesJson(datos.getDetallesJson());
        cotizacion.setRutaArchivoPdf(datos.getRutaArchivoPdf());
        cotizacion.setAnio(datos.getAnio());
        cotizacion.setCodigo(datos.getCodigo());
        cotizacion.setCorrelativo(datos.getCorrelativo());

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(cotizacion));
    }
}

