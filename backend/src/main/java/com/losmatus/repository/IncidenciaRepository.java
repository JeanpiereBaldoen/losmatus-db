package com.losmatus.repository;

import com.losmatus.entity.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    List<Incidencia> findByIdUsuarioReporta(Long idUsuarioReporta);
    List<Incidencia> findByIdEstadoIncidencia(Long idEstadoIncidencia);
    Optional<Incidencia> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
}
