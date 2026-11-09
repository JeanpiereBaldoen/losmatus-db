package com.losmatus.controller;

import com.losmatus.entity.Area;
import com.losmatus.repository.AreaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/areas")
public class AreaController extends CrudBaseController<Area> {

    public AreaController(AreaRepository repositorio) {
        super(repositorio, Area.class);
    }
}
