package com.losmatus.repository;

import com.losmatus.entity.CostoIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CostoIncidenciaRepository extends JpaRepository<CostoIncidencia, Long> {
    List<CostoIncidencia> findByIdIncidencia(Long idIncidencia);
}
