package com.losmatus.controller;

import com.losmatus.entity.Categoria;
import com.losmatus.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController extends CrudBaseController<Categoria> {

    public CategoriaController(CategoriaRepository repositorio) {
        super(repositorio, Categoria.class);
    }
}
