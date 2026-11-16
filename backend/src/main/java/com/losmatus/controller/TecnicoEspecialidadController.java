package com.losmatus.controller;

import com.losmatus.entity.TecnicoEspecialidad;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.TecnicoEspecialidadRepository;
import com.losmatus.repository.TecnicoRepository;
import com.losmatus.repository.TipoEspecialidadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos/{idTecnico}/especialidades")
public class TecnicoEspecialidadController {

    private final TecnicoEspecialidadRepository repo;
    private final TecnicoRepository tecnicoRepository;
    private final TipoEspecialidadRepository especialidadRepository;

    public TecnicoEspecialidadController(
            TecnicoEspecialidadRepository repo,
            TecnicoRepository tecnicoRepository,
            TipoEspecialidadRepository especialidadRepository
    ) {
        this.repo = repo;
        this.tecnicoRepository = tecnicoRepository;
        this.especialidadRepository = especialidadRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<TecnicoEspecialidad> listarPorTecnico(@PathVariable Long idTecnico) {
        if (!tecnicoRepository.existsById(idTecnico)) {
            throw new ResourceNotFoundException("Tecnico no encontrado con ID: " + idTecnico);
        }
        return repo.findByIdTecnico(idTecnico);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<TecnicoEspecialidad> asignarEspecialidad(
            @PathVariable Long idTecnico,
            @RequestBody TecnicoEspecialidad datos
    ) {
        if (!tecnicoRepository.existsById(idTecnico)) {
            throw new ResourceNotFoundException("Tecnico no encontrado con ID: " + idTecnico);
        }
        if (!especialidadRepository.existsById(datos.getIdEspecialidad())) {
            throw new ResourceNotFoundException("Especialidad no encontrada con ID: " + datos.getIdEspecialidad());
        }

        TecnicoEspecialidad nuevo = new TecnicoEspecialidad();
        nuevo.setIdTecnico(idTecnico);
        nuevo.setIdEspecialidad(datos.getIdEspecialidad());
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(nuevo));
    }

    @DeleteMapping("/{idEspecialidad}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Void> eliminarEspecialidad(
            @PathVariable Long idTecnico,
            @PathVariable Long idEspecialidad
    ) {
        List<TecnicoEspecialidad> lista = repo.findByIdTecnico(idTecnico);
        for (TecnicoEspecialidad te : lista) {
            if (te.getIdEspecialidad().equals(idEspecialidad)) {
                repo.delete(te);
                return ResponseEntity.noContent().build();
            }
        }
        throw new ResourceNotFoundException("Especialidad no asociada al tecnico");
    }
}

