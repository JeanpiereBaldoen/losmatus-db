package com.losmatus.controller;

import com.losmatus.config.SecurityUtils;
import com.losmatus.entity.ArticuloKb;
import com.losmatus.exception.BadRequestException;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.ArticuloKbRepository;
import com.losmatus.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/articulos-kb")
public class ArticuloKbController {

    private final ArticuloKbRepository repo;
    private final CategoriaRepository categoriaRepo;

    public ArticuloKbController(ArticuloKbRepository repo, CategoriaRepository categoriaRepo) {
        this.repo = repo;
        this.categoriaRepo = categoriaRepo;
    }

    @GetMapping("/publicados")
    @Transactional(readOnly = true)
    public ResponseEntity<List<ArticuloKb>> listarPublicados() {
        return ResponseEntity.ok(repo.findByEstado(ArticuloKb.Estado.PUBLICADO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    @Transactional(readOnly = true)
    public ResponseEntity<List<ArticuloKb>> listarTodos() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ArticuloKb> obtenerPorId(@PathVariable Long id) {
        ArticuloKb articulo = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + id));

        if (SecurityUtils.isCliente() && articulo.getEstado() != ArticuloKb.Estado.PUBLICADO) {
            throw new ResourceNotFoundException("Articulo no disponible para consulta publica");
        }

        return ResponseEntity.ok(articulo);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    @Transactional
    public ResponseEntity<ArticuloKb> crear(@RequestBody ArticuloKb datos) {
        if (datos.getTitulo() == null || datos.getTitulo().isBlank()) {
            throw new BadRequestException("El titulo del articulo es obligatorio");
        }
        if (datos.getSolucion() == null || datos.getSolucion().isBlank()) {
            throw new BadRequestException("La solucion del articulo es obligatoria");
        }
        if (datos.getIdCategoria() != null && !categoriaRepo.existsById(datos.getIdCategoria())) {
            throw new ResourceNotFoundException("Categoria no encontrada con ID: " + datos.getIdCategoria());
        }

        ArticuloKb nuevo = new ArticuloKb();
        nuevo.setTitulo(datos.getTitulo().trim());
        nuevo.setSolucion(datos.getSolucion().trim());
        nuevo.setPalabrasClave(datos.getPalabrasClave() != null ? datos.getPalabrasClave().trim() : null);
        nuevo.setIdCategoria(datos.getIdCategoria());
        nuevo.setIdAutor(SecurityUtils.getCurrentUserId());
        nuevo.setIdIncidenciaOrigen(datos.getIdIncidenciaOrigen());
        nuevo.setEstado(datos.getEstado() != null ? datos.getEstado() : ArticuloKb.Estado.BORRADOR);
        nuevo.setFechaCreacion(LocalDateTime.now());

        if (nuevo.getEstado() == ArticuloKb.Estado.PUBLICADO) {
            nuevo.setFechaPublicacion(LocalDateTime.now());
            nuevo.setIdAprobador(SecurityUtils.getCurrentUserId());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(nuevo));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    @Transactional
    public ResponseEntity<ArticuloKb> actualizar(@PathVariable Long id, @RequestBody ArticuloKb datos) {
        ArticuloKb articulo = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + id));

        if (datos.getTitulo() != null) articulo.setTitulo(datos.getTitulo().trim());
        if (datos.getSolucion() != null) articulo.setSolucion(datos.getSolucion().trim());
        if (datos.getPalabrasClave() != null) articulo.setPalabrasClave(datos.getPalabrasClave().trim());
        if (datos.getIdCategoria() != null) articulo.setIdCategoria(datos.getIdCategoria());

        if (datos.getEstado() != null && datos.getEstado() != articulo.getEstado()) {
            articulo.setEstado(datos.getEstado());
            if (datos.getEstado() == ArticuloKb.Estado.PUBLICADO && articulo.getFechaPublicacion() == null) {
                articulo.setFechaPublicacion(LocalDateTime.now());
                articulo.setIdAprobador(SecurityUtils.getCurrentUserId());
            }
        }

        return ResponseEntity.ok(repo.save(articulo));
    }
}

