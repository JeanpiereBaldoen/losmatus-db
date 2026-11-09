package com.losmatus.controller;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * CRUD de desarrollo para las entidades de Los Matus.
 * Solo copia columnas escalares; nunca modifica identificadores o relaciones JPA.
 * Poner autenticacion y autorizacion antes de publicar estos endpoints.
 */
public abstract class CrudBaseController<T> {

    private final JpaRepository<T, Long> repositorio;
    private final Class<T> tipo;
    private final List<Field> columnas;

    protected CrudBaseController(JpaRepository<T, Long> repositorio, Class<T> tipo) {
        this.repositorio = repositorio;
        this.tipo = tipo;
        this.columnas = Arrays.stream(tipo.getDeclaredFields())
                .filter(c -> c.isAnnotationPresent(Column.class))
                .filter(c -> !c.isAnnotationPresent(Id.class))
                .toList();
        this.columnas.forEach(c -> c.setAccessible(true));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<T> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public T obtenerPorId(@PathVariable Long id) {
        return encontrar(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<T> crear(@RequestBody T datos) {
        T nuevo = instanciar();
        copiarColumnas(datos, nuevo);
        comprobarRequeridos(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(repositorio.saveAndFlush(nuevo));
    }

    @PutMapping("/{id}")
    @Transactional
    public T actualizar(@PathVariable Long id, @RequestBody T datos) {
        T existente = encontrar(id);
        copiarColumnas(datos, existente);
        comprobarRequeridos(existente);
        return repositorio.saveAndFlush(existente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        T existente = encontrar(id);
        repositorio.delete(existente);
        repositorio.flush();
        return ResponseEntity.noContent().build();
    }

    private T encontrar(Long id) {
        return repositorio.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        tipo.getSimpleName() + " no encontrado con ID: " + id));
    }

    private T instanciar() {
        try {
            return tipo.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("No se puede crear " + tipo.getSimpleName(), ex);
        }
    }

    private void copiarColumnas(T origen, T destino) {
        for (Field columna : columnas) {
            try {
                columna.set(destino, columna.get(origen));
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("Error al copiar " + columna.getName(), ex);
            }
        }
    }

    private void comprobarRequeridos(T entidad) {
        for (Field columna : columnas) {
            if (!columna.getAnnotation(Column.class).nullable()) {
                try {
                    if (columna.get(entidad) == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Campo obligatorio: " + columna.getName());
                    }
                } catch (IllegalAccessException ex) {
                    throw new IllegalStateException("Error al validar " + columna.getName(), ex);
                }
            }
        }
    }
}
