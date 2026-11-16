package com.losmatus.repository;

import com.losmatus.entity.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByUsuarioId(Long usuarioId);
    Optional<Suscripcion> findByUsuarioIdAndModuloId(Long usuarioId, Long moduloId);
}
