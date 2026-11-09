package com.losmatus.controller;

import com.losmatus.entity.Tecnico;
import com.losmatus.repository.TecnicoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController extends CrudBaseController<Tecnico> {

    public TecnicoController(TecnicoRepository repositorio) {
        super(repositorio, Tecnico.class);
    }
}
