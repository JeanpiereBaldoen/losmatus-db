package com.losmatus.service;

import com.losmatus.entity.Incidencia;
import com.losmatus.repository.IncidenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;

    public IncidenciaService(IncidenciaRepository incidenciaRepository) {
        this.incidenciaRepository = incidenciaRepository;
    }

    @Transactional(readOnly = true)
    public List<Incidencia> listarTodas() {
        return incidenciaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Incidencia buscarPorId(Long id) {
        return incidenciaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Incidencia no encontrada con ID: " + id));
    }
}