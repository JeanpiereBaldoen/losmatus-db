package com.losmatus.repository;

import com.losmatus.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByUsuarioId(Long usuarioId);
    boolean existsByDni(String dni);
    Optional<Persona> findByDni(String dni);
}
