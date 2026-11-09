package com.losmatus.controller;

import com.losmatus.entity.Rol;
import com.losmatus.repository.RolRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolConsultaController {

    private final RolRepository repositorio;

    public RolConsultaController(RolRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<Rol> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public Rol obtener(@PathVariable Long id) {
        return repositorio.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Rol no encontrado con ID: " + id));
    }
}
