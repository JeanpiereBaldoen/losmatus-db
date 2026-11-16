package com.losmatus.repository;

import com.losmatus.entity.ImagenIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenIncidenciaRepository extends JpaRepository<ImagenIncidencia, Long> {
    List<ImagenIncidencia> findByIdIncidencia(Long idIncidencia);
}
