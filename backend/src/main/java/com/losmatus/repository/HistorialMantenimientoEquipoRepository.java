package com.losmatus.repository;

import com.losmatus.entity.HistorialMantenimientoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialMantenimientoEquipoRepository extends JpaRepository<HistorialMantenimientoEquipo, Long> {
    List<HistorialMantenimientoEquipo> findByIdEquipo(Long idEquipo);
}
