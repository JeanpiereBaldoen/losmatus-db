package com.losmatus.controller;

import com.losmatus.dto.EncuestaSatisfaccionRequest;
import com.losmatus.entity.EncuestaSatisfaccion;
import com.losmatus.exception.BadRequestException;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.EncuestaSatisfaccionRepository;
import com.losmatus.service.IncidenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/incidencias/{idIncidencia}/encuesta")
public class EncuestaSatisfaccionController {

    private final EncuestaSatisfaccionRepository encuestaRepo;
    private final IncidenciaService incidenciaService;

    public EncuestaSatisfaccionController(
            EncuestaSatisfaccionRepository encuestaRepo,
            IncidenciaService incidenciaService
    ) {
        this.encuestaRepo = encuestaRepo;
        this.incidenciaService = incidenciaService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<EncuestaSatisfaccion> obtenerPorIncidencia(@PathVariable Long idIncidencia) {
        incidenciaService.buscarPorId(idIncidencia); // Valida propiedad
        return encuestaRepo.findByIdIncidencia(idIncidencia)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha registrado encuesta para esta incidencia"));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EncuestaSatisfaccion> registrarEncuesta(
            @PathVariable Long idIncidencia,
            @Valid @RequestBody EncuestaSatisfaccionRequest request
    ) {
        incidenciaService.buscarPorId(idIncidencia); // Valida propiedad del cliente

        if (encuestaRepo.existsByIdIncidencia(idIncidencia)) {
            throw new BadRequestException("Ya existe una encuesta registrada para esta incidencia");
        }

        EncuestaSatisfaccion encuesta = new EncuestaSatisfaccion();
        encuesta.setIdIncidencia(idIncidencia);
        encuesta.setRespuesta1(request.respuesta1());
        encuesta.setRespuesta2(request.respuesta2());
        encuesta.setRespuesta3(request.respuesta3());
        encuesta.setComentarioGeneral(request.comentarioGeneral() != null ? request.comentarioGeneral().trim() : null);
        encuesta.setFechaEncuesta(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(encuestaRepo.save(encuesta));
    }
}

