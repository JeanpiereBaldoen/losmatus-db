package com.losmatus.repository;

import com.losmatus.entity.EncuestaSatisfaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EncuestaSatisfaccionRepository extends JpaRepository<EncuestaSatisfaccion, Long> {
    Optional<EncuestaSatisfaccion> findByIdIncidencia(Long idIncidencia);
    boolean existsByIdIncidencia(Long idIncidencia);
}
