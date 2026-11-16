package com.losmatus.controller;

import com.losmatus.dto.CostoSolicitudRequest;
import com.losmatus.entity.CostoSolicitud;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.CostoSolicitudRepository;
import com.losmatus.repository.ProductoRepository;
import com.losmatus.repository.ProveedorRepository;
import com.losmatus.repository.SolicitudServicioRepository;
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
@RequestMapping("/api/solicitudes-servicio/{idSolicitud}/costos")
@PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
public class CostoSolicitudController {

    private final CostoSolicitudRepository repo;
    private final SolicitudServicioRepository solicitudRepo;
    private final TipoCostoRepository tipoCostoRepo;
    private final ProductoRepository productoRepo;
    private final ProveedorRepository proveedorRepo;

    public CostoSolicitudController(
            CostoSolicitudRepository repo,
            SolicitudServicioRepository solicitudRepo,
            TipoCostoRepository tipoCostoRepo,
            ProductoRepository productoRepo,
            ProveedorRepository proveedorRepo
    ) {
        this.repo = repo;
        this.solicitudRepo = solicitudRepo;
        this.tipoCostoRepo = tipoCostoRepo;
        this.productoRepo = productoRepo;
        this.proveedorRepo = proveedorRepo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<CostoSolicitud>> listarPorSolicitud(@PathVariable Long idSolicitud) {
        if (!solicitudRepo.existsById(idSolicitud)) {
            throw new ResourceNotFoundException("Solicitud no encontrada con ID: " + idSolicitud);
        }
        return ResponseEntity.ok(repo.findByIdSolicitud(idSolicitud));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CostoSolicitud> registrarCosto(
            @PathVariable Long idSolicitud,
            @Valid @RequestBody CostoSolicitudRequest request
    ) {
        if (!solicitudRepo.existsById(idSolicitud)) {
            throw new ResourceNotFoundException("Solicitud no encontrada con ID: " + idSolicitud);
        }
        if (!tipoCostoRepo.existsById(request.idTipoCosto())) {
            throw new ResourceNotFoundException("Tipo de costo no encontrado con ID: " + request.idTipoCosto());
        }
        if (request.idProducto() != null && !productoRepo.existsById(request.idProducto())) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + request.idProducto());
        }
        if (request.idProveedor() != null && !proveedorRepo.existsById(request.idProveedor())) {
            throw new ResourceNotFoundException("Proveedor no encontrado con ID: " + request.idProveedor());
        }

        CostoSolicitud costo = new CostoSolicitud();
        costo.setIdSolicitud(idSolicitud);
        costo.setTitulo(request.titulo().trim());
        costo.setDescripcion(request.descripcion() != null ? request.descripcion().trim() : null);
        costo.setCantidad(request.cantidad() != null ? request.cantidad() : 1);
        costo.setMonto(request.monto() != null ? request.monto() : 0.0);
        costo.setCostoProduccion(request.costoProduccion());
        costo.setMontoTotal(request.montoTotal() != null ? request.montoTotal() : (costo.getCantidad() * costo.getMonto()));
        costo.setIdTipoCosto(request.idTipoCosto());
        costo.setIdProducto(request.idProducto());
        costo.setIdProveedor(request.idProveedor());
        costo.setFecha(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(costo));
    }
}

