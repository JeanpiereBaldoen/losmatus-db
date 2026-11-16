package com.losmatus.repository;

import com.losmatus.entity.SolicitudServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SolicitudServicioRepository extends JpaRepository<SolicitudServicio, Long> {
    List<SolicitudServicio> findByIdUsuario(Long idUsuario);
    Optional<SolicitudServicio> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);

    @Query("SELECT MAX(s.correlativo) FROM SolicitudServicio s WHERE s.anio = :anio")
    Optional<Integer> findMaxCorrelativoByAnio(@Param("anio") Integer anio);
}
