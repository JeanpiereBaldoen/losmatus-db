package com.losmatus.controller;

import com.losmatus.entity.Proveedor;
import com.losmatus.repository.ProveedorRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController extends CrudBaseController<Proveedor> {

    public ProveedorController(ProveedorRepository repositorio) {
        super(repositorio, Proveedor.class);
    }
}
