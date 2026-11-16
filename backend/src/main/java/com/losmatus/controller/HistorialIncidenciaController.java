package com.losmatus.controller;

import com.losmatus.entity.HistorialIncidencia;
import com.losmatus.repository.HistorialIncidenciaRepository;
import com.losmatus.service.IncidenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias/{idIncidencia}/historial")
public class HistorialIncidenciaController {

    private final HistorialIncidenciaRepository historialRepo;
    private final IncidenciaService incidenciaService;

    public HistorialIncidenciaController(
            HistorialIncidenciaRepository historialRepo,
            IncidenciaService incidenciaService
    ) {
        this.historialRepo = historialRepo;
        this.incidenciaService = incidenciaService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<HistorialIncidencia>> listarPorIncidencia(@PathVariable Long idIncidencia) {
        incidenciaService.buscarPorId(idIncidencia); // Valida acceso
        return ResponseEntity.ok(historialRepo.findByIdIncidenciaOrderByFechaCambioDesc(idIncidencia));
    }
}

