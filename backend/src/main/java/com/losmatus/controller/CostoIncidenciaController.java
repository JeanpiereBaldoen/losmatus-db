package com.losmatus.controller;

import com.losmatus.config.SecurityUtils;
import com.losmatus.dto.CostoIncidenciaRequest;
import com.losmatus.entity.CostoIncidencia;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.CostoIncidenciaRepository;
import com.losmatus.repository.IncidenciaRepository;
import com.losmatus.repository.ProveedorRepository;
import com.losmatus.repository.TipoCostoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/incidencias/{idIncidencia}/costos")
@PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
public class CostoIncidenciaController {

    private final CostoIncidenciaRepository costoRepo;
    private final IncidenciaRepository incidenciaRepository;
    private final TipoCostoRepository tipoCostoRepository;
    private final ProveedorRepository proveedorRepository;

    public CostoIncidenciaController(
            CostoIncidenciaRepository costoRepo,
            IncidenciaRepository incidenciaRepository,
            TipoCostoRepository tipoCostoRepository,
            ProveedorRepository proveedorRepository
    ) {
        this.costoRepo = costoRepo;
        this.incidenciaRepository = incidenciaRepository;
        this.tipoCostoRepository = tipoCostoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<CostoIncidencia>> listarPorIncidencia(@PathVariable Long idIncidencia) {
        if (!incidenciaRepository.existsById(idIncidencia)) {
            throw new ResourceNotFoundException("Incidencia no encontrada con ID: " + idIncidencia);
        }
        return ResponseEntity.ok(costoRepo.findByIdIncidencia(idIncidencia));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CostoIncidencia> registrarCosto(
            @PathVariable Long idIncidencia,
            @Valid @RequestBody CostoIncidenciaRequest request
    ) {
        if (!incidenciaRepository.existsById(idIncidencia)) {
            throw new ResourceNotFoundException("Incidencia no encontrada con ID: " + idIncidencia);
        }
        if (!tipoCostoRepository.existsById(request.idTipoCosto())) {
            throw new ResourceNotFoundException("Tipo de costo no encontrado con ID: " + request.idTipoCosto());
        }
        if (request.idProveedor() != null && !proveedorRepository.existsById(request.idProveedor())) {
            throw new ResourceNotFoundException("Proveedor no encontrado con ID: " + request.idProveedor());
        }

        CostoIncidencia costo = new CostoIncidencia();
        costo.setIdIncidencia(idIncidencia);
        costo.setCostoProduccion(request.costoProduccion());
        costo.setCostoVenta(request.costoVenta());
        costo.setGanancia(request.ganancia() != null ? request.ganancia() : (request.costoVenta() - request.costoProduccion()));
        costo.setIgv(request.igv() != null ? request.igv() : (request.costoVenta() * 0.18));
        costo.setCostoTotal(request.costoTotal() != null ? request.costoTotal() : (costo.getCostoVenta() + costo.getIgv()));
        costo.setIdTipoCosto(request.idTipoCosto());
        costo.setIdProveedor(request.idProveedor());
        costo.setIdUsuario(SecurityUtils.getCurrentUserId());
        costo.setFechaRegistro(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(costoRepo.save(costo));
    }
}

