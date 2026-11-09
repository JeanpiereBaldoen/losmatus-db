package com.losmatus.controller;

import com.losmatus.entity.Severidad;
import com.losmatus.repository.SeveridadRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/severidades")
public class SeveridadController extends CrudBaseController<Severidad> {

    public SeveridadController(SeveridadRepository repositorio) {
        super(repositorio, Severidad.class);
    }
}
