package com.losmatus.repository;

import com.losmatus.entity.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtencionRepository extends JpaRepository<Atencion, Long> {
    List<Atencion> findByIdIncidencia(Long idIncidencia);
    List<Atencion> findByIdTecnico(Long idTecnico);
}
