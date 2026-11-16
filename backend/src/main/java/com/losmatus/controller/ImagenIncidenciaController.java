package com.losmatus.controller;

import com.losmatus.dto.ImagenIncidenciaRequest;
import com.losmatus.entity.ImagenIncidencia;
import com.losmatus.repository.ImagenIncidenciaRepository;
import com.losmatus.service.IncidenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/incidencias/{idIncidencia}/imagenes")
public class ImagenIncidenciaController {

    private final ImagenIncidenciaRepository imagenRepo;
    private final IncidenciaService incidenciaService;

    public ImagenIncidenciaController(
            ImagenIncidenciaRepository imagenRepo,
            IncidenciaService incidenciaService
    ) {
        this.imagenRepo = imagenRepo;
        this.incidenciaService = incidenciaService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ImagenIncidencia>> listarPorIncidencia(@PathVariable Long idIncidencia) {
        incidenciaService.buscarPorId(idIncidencia); // Valida acceso
        return ResponseEntity.ok(imagenRepo.findByIdIncidencia(idIncidencia));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ImagenIncidencia> agregarImagen(
            @PathVariable Long idIncidencia,
            @Valid @RequestBody ImagenIncidenciaRequest request
    ) {
        incidenciaService.buscarPorId(idIncidencia); // Valida acceso

        ImagenIncidencia imagen = new ImagenIncidencia();
        imagen.setIdIncidencia(idIncidencia);
        imagen.setUrlImagen(request.urlImagen().trim());
        imagen.setFechaSubida(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(imagenRepo.save(imagen));
    }
}

