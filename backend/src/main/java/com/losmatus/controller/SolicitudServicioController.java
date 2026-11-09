package com.losmatus.controller;

import com.losmatus.entity.SolicitudServicio;
import com.losmatus.repository.SolicitudServicioRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitudes-servicio")
public class SolicitudServicioController extends CrudBaseController<SolicitudServicio> {

    public SolicitudServicioController(SolicitudServicioRepository repositorio) {
        super(repositorio, SolicitudServicio.class);
    }
}
