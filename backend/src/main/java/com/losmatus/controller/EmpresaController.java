package com.losmatus.controller;

import com.losmatus.entity.Empresa;
import com.losmatus.repository.EmpresaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController extends CrudBaseController<Empresa> {

    public EmpresaController(EmpresaRepository repositorio) {
        super(repositorio, Empresa.class);
    }
}
