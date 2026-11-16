package com.losmatus.controller;

import com.losmatus.entity.ImagenSolicitud;
import com.losmatus.repository.ImagenSolicitudRepository;
import com.losmatus.service.SolicitudServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes-servicio/{idSolicitud}/imagenes")
public class ImagenSolicitudController {

    private final ImagenSolicitudRepository repo;
    private final SolicitudServicioService service;

    public ImagenSolicitudController(
            ImagenSolicitudRepository repo,
            SolicitudServicioService service
    ) {
        this.repo = repo;
        this.service = service;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ImagenSolicitud>> listarPorSolicitud(@PathVariable Long idSolicitud) {
        service.buscarPorId(idSolicitud); // Valida acceso
        return ResponseEntity.ok(repo.findBySolicitudId(idSolicitud));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ImagenSolicitud> agregarImagen(
            @PathVariable Long idSolicitud,
            @RequestBody ImagenSolicitud datos
    ) {
        service.buscarPorId(idSolicitud);

        ImagenSolicitud imagen = new ImagenSolicitud();
        imagen.setSolicitudId(idSolicitud);
        imagen.setUrlImagen(datos.getUrlImagen());
        imagen.setTipoDocumento(datos.getTipoDocumento());
        imagen.setFechaSubida(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(imagen));
    }
}

