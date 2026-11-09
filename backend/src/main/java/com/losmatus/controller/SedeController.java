package com.losmatus.controller;

import com.losmatus.entity.Sede;
import com.losmatus.repository.SedeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sedes")
public class SedeController extends CrudBaseController<Sede> {

    public SedeController(SedeRepository repositorio) {
        super(repositorio, Sede.class);
    }
}
