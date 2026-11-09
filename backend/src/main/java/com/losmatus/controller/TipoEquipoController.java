package com.losmatus.controller;

import com.losmatus.entity.TipoEquipo;
import com.losmatus.repository.TipoEquipoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tipos-equipo")
public class TipoEquipoController extends CrudBaseController<TipoEquipo> {

    public TipoEquipoController(TipoEquipoRepository repositorio) {
        super(repositorio, TipoEquipo.class);
    }
}
