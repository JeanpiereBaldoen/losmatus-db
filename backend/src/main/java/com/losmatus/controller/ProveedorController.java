package com.losmatus.controller;

import com.losmatus.entity.Proveedor;
import com.losmatus.repository.ProveedorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proveedores")
@PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
public class ProveedorController extends CrudBaseController<Proveedor> {

    public ProveedorController(ProveedorRepository repositorio) {
        super(repositorio, Proveedor.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Proveedor> crear(Proveedor datos) {
        return super.crear(datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Proveedor actualizar(Long id, Proveedor datos) {
        return super.actualizar(id, datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(Long id) {
        return super.eliminar(id);
    }
}
