package com.losmatus.controller;

import com.losmatus.entity.HistorialSolicitudServicio;
import com.losmatus.repository.HistorialSolicitudServicioRepository;
import com.losmatus.service.SolicitudServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes-servicio/{idSolicitud}/historial")
public class HistorialSolicitudController {

    private final HistorialSolicitudServicioRepository repo;
    private final SolicitudServicioService service;

    public HistorialSolicitudController(
            HistorialSolicitudServicioRepository repo,
            SolicitudServicioService service
    ) {
        this.repo = repo;
        this.service = service;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<HistorialSolicitudServicio>> listarPorSolicitud(@PathVariable Long idSolicitud) {
        service.buscarPorId(idSolicitud); // Valida propiedad / existencia
        return ResponseEntity.ok(repo.findByIdSolicitudOrderByFechaCambioDesc(idSolicitud));
    }
}

