package com.losmatus.repository;

import com.losmatus.entity.HistorialIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialIncidenciaRepository extends JpaRepository<HistorialIncidencia, Long> {
    List<HistorialIncidencia> findByIdIncidenciaOrderByFechaCambioDesc(Long idIncidencia);
}
