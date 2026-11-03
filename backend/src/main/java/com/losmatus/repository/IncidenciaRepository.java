package com.losmatus.repository;

import com.losmatus.entity.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    Optional<Incidencia> findByCodigo(String codigo);
}
