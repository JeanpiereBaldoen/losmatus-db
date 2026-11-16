package com.losmatus.controller;

import com.losmatus.entity.Categoria;
import com.losmatus.repository.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController extends CrudBaseController<Categoria> {

    public CategoriaController(CategoriaRepository repositorio) {
        super(repositorio, Categoria.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categoria> crear(Categoria datos) {
        return super.crear(datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Categoria actualizar(Long id, Categoria datos) {
        return super.actualizar(id, datos);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(Long id) {
        return super.eliminar(id);
    }
}
