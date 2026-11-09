package com.losmatus.controller;

import com.losmatus.entity.EstadoTecnico;
import com.losmatus.repository.EstadoTecnicoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estados-tecnico")
public class EstadoTecnicoController extends CrudBaseController<EstadoTecnico> {

    public EstadoTecnicoController(EstadoTecnicoRepository repositorio) {
        super(repositorio, EstadoTecnico.class);
    }
}
