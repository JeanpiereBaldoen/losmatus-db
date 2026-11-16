package com.losmatus.controller;

import com.losmatus.entity.Producto;
import com.losmatus.exception.BadRequestException;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository repo;

    public ProductoController(ProductoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Producto> crear(@RequestBody Producto datos) {
        if (datos.getNombre() == null || datos.getNombre().isBlank()) {
            throw new BadRequestException("El nombre del producto es obligatorio");
        }

        Producto nuevo = new Producto();
        nuevo.setNombre(datos.getNombre().trim());
        nuevo.setDescripcion(datos.getDescripcion() != null ? datos.getDescripcion().trim() : null);
        nuevo.setPrecioProduccion(datos.getPrecioProduccion());
        nuevo.setPrecioVenta(datos.getPrecioVenta());

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(nuevo));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto datos) {
        Producto existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        if (datos.getNombre() != null) existente.setNombre(datos.getNombre().trim());
        if (datos.getDescripcion() != null) existente.setDescripcion(datos.getDescripcion().trim());
        if (datos.getPrecioProduccion() != null) existente.setPrecioProduccion(datos.getPrecioProduccion());
        if (datos.getPrecioVenta() != null) existente.setPrecioVenta(datos.getPrecioVenta());

        return ResponseEntity.ok(repo.save(existente));
    }
}

