package com.losmatus.controller;

import com.losmatus.entity.TipoServicio;
import com.losmatus.repository.TipoServicioRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tipos-servicio")
public class TipoServicioController extends CrudBaseController<TipoServicio> {

    public TipoServicioController(TipoServicioRepository repositorio) {
        super(repositorio, TipoServicio.class);
    }
}
