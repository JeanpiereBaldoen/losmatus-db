package com.losmatus.controller;

import com.losmatus.entity.EstadoSolicitudServicio;
import com.losmatus.repository.EstadoSolicitudServicioRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estados-solicitud")
public class EstadoSolicitudServicioController extends CrudBaseController<EstadoSolicitudServicio> {

    public EstadoSolicitudServicioController(EstadoSolicitudServicioRepository repositorio) {
        super(repositorio, EstadoSolicitudServicio.class);
    }
}
