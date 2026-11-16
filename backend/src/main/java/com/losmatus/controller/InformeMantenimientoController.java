package com.losmatus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losmatus.config.SecurityUtils;
import com.losmatus.entity.AuditoriaInforme;
import com.losmatus.entity.InformeMantenimiento;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.AuditoriaInformeRepository;
import com.losmatus.repository.EquipoRepository;
import com.losmatus.repository.InformeMantenimientoRepository;
import com.losmatus.repository.PlantillaChecklistRepository;
import com.losmatus.repository.TecnicoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/informes-mantenimiento")
@PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
public class InformeMantenimientoController {

    private final InformeMantenimientoRepository repo;
    private final AuditoriaInformeRepository auditoriaRepo;
    private final EquipoRepository equipoRepo;
    private final TecnicoRepository tecnicoRepo;
    private final PlantillaChecklistRepository plantillaRepo;
    private final ObjectMapper objectMapper;

    public InformeMantenimientoController(
            InformeMantenimientoRepository repo,
            AuditoriaInformeRepository auditoriaRepo,
            EquipoRepository equipoRepo,
            TecnicoRepository tecnicoRepo,
            PlantillaChecklistRepository plantillaRepo
    ) {
        this.repo = repo;
        this.auditoriaRepo = auditoriaRepo;
        this.equipoRepo = equipoRepo;
        this.tecnicoRepo = tecnicoRepo;
        this.plantillaRepo = plantillaRepo;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<InformeMantenimiento>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<InformeMantenimiento> obtenerPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Informe no encontrado con ID: " + id));
    }

    @GetMapping("/equipo/{idEquipo}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<InformeMantenimiento>> listarPorEquipo(@PathVariable Long idEquipo) {
        return ResponseEntity.ok(repo.findByIdEquipo(idEquipo));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<InformeMantenimiento> crear(@RequestBody InformeMantenimiento datos) {
        if (datos.getIdEquipo() != null && !equipoRepo.existsById(datos.getIdEquipo())) {
            throw new ResourceNotFoundException("Equipo no encontrado con ID: " + datos.getIdEquipo());
        }
        if (datos.getIdTecnico() != null && !tecnicoRepo.existsById(datos.getIdTecnico())) {
            throw new ResourceNotFoundException("Tecnico no encontrado con ID: " + datos.getIdTecnico());
        }
        if (datos.getIdPlantilla() != null && !plantillaRepo.existsById(datos.getIdPlantilla())) {
            throw new ResourceNotFoundException("Plantilla no encontrada con ID: " + datos.getIdPlantilla());
        }

        InformeMantenimiento nuevo = new InformeMantenimiento();
        nuevo.setCodigo(datos.getCodigo());
        nuevo.setEstado(datos.getEstado() != null ? datos.getEstado() : InformeMantenimiento.Estado.BORRADOR);
        nuevo.setFechaEjecucion(datos.getFechaEjecucion() != null ? datos.getFechaEjecucion() : LocalDateTime.now());
        nuevo.setDatosDinamicos(datos.getDatosDinamicos());
        nuevo.setFotosUrl(datos.getFotosUrl());
        nuevo.setIdEquipo(datos.getIdEquipo());
        nuevo.setIdTecnico(datos.getIdTecnico());
        nuevo.setIdPlantilla(datos.getIdPlantilla());

        InformeMantenimiento guardado = repo.save(nuevo);
        registrarAuditoria(guardado.getId(), "CREAR", null, serializar(guardado));

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<InformeMantenimiento> actualizar(@PathVariable Long id, @RequestBody InformeMantenimiento datos) {
        InformeMantenimiento existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Informe no encontrado con ID: " + id));

        String estadoAnterior = serializar(existente);

        if (datos.getCodigo() != null) existente.setCodigo(datos.getCodigo());
        if (datos.getEstado() != null) existente.setEstado(datos.getEstado());
        if (datos.getFechaEjecucion() != null) existente.setFechaEjecucion(datos.getFechaEjecucion());
        if (datos.getDatosDinamicos() != null) existente.setDatosDinamicos(datos.getDatosDinamicos());
        if (datos.getFotosUrl() != null) existente.setFotosUrl(datos.getFotosUrl());
        if (datos.getIdEquipo() != null) existente.setIdEquipo(datos.getIdEquipo());
        if (datos.getIdTecnico() != null) existente.setIdTecnico(datos.getIdTecnico());
        if (datos.getIdPlantilla() != null) existente.setIdPlantilla(datos.getIdPlantilla());

        InformeMantenimiento actualizado = repo.save(existente);
        registrarAuditoria(actualizado.getId(), "ACTUALIZAR", estadoAnterior, serializar(actualizado));

        return ResponseEntity.ok(actualizado);
    }

    private void registrarAuditoria(Long idInforme, String accion, String anterior, String nuevo) {
        try {
            AuditoriaInforme aud = new AuditoriaInforme();
            aud.setIdInforme(idInforme);
            aud.setAccion(accion);
            aud.setDatosAnteriores(anterior);
            aud.setDatosNuevos(nuevo);
            aud.setFechaCambio(LocalDateTime.now());
            aud.setUsuarioResponsable(SecurityUtils.getCurrentUserEmail());
            auditoriaRepo.save(aud);
        } catch (Exception ignored) {
            // Auditoria defensiva
        }
    }

    private String serializar(InformeMantenimiento inf) {
        try {
            return objectMapper.writeValueAsString(inf);
        } catch (Exception e) {
            return null;
        }
    }
}

