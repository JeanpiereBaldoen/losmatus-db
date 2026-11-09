package com.losmatus.controller;

import com.losmatus.entity.ModoAtencion;
import com.losmatus.repository.ModoAtencionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modos-atencion")
public class ModoAtencionController extends CrudBaseController<ModoAtencion> {

    public ModoAtencionController(ModoAtencionRepository repositorio) {
        super(repositorio, ModoAtencion.class);
    }
}
