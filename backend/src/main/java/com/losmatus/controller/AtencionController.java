package com.losmatus.controller;

import com.losmatus.entity.Atencion;
import com.losmatus.repository.AtencionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/atenciones")
public class AtencionController extends CrudBaseController<Atencion> {

    public AtencionController(AtencionRepository repositorio) {
        super(repositorio, Atencion.class);
    }
}
