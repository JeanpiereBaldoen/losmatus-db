package com.losmatus.controller;

import com.losmatus.entity.Equipo;
import com.losmatus.repository.EquipoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController extends CrudBaseController<Equipo> {

    public EquipoController(EquipoRepository repositorio) {
        super(repositorio, Equipo.class);
    }
}
