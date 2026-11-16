package com.losmatus.repository;

import com.losmatus.entity.InformeMantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InformeMantenimientoRepository extends JpaRepository<InformeMantenimiento, Long> {
    List<InformeMantenimiento> findByIdEquipo(Long idEquipo);
    List<InformeMantenimiento> findByIdTecnico(Long idTecnico);
}
