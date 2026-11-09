package com.losmatus.controller;

import com.losmatus.entity.EstadoIncidencia;
import com.losmatus.repository.EstadoIncidenciaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estados-incidencia")
public class EstadoIncidenciaController extends CrudBaseController<EstadoIncidencia> {

    public EstadoIncidenciaController(EstadoIncidenciaRepository repositorio) {
        super(repositorio, EstadoIncidencia.class);
    }
}
