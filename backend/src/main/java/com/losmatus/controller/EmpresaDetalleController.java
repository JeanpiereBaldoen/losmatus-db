package com.losmatus.controller;

import com.losmatus.entity.EmpresaDetalle;
import com.losmatus.repository.EmpresaDetalleRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empresas-detalle")
public class EmpresaDetalleController extends CrudBaseController<EmpresaDetalle> {

    public EmpresaDetalleController(EmpresaDetalleRepository repositorio) {
        super(repositorio, EmpresaDetalle.class);
    }
}
