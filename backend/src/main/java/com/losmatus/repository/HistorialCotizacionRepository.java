package com.losmatus.repository;

import com.losmatus.entity.HistorialCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialCotizacionRepository extends JpaRepository<HistorialCotizacion, Long> {
    List<HistorialCotizacion> findByIdSolicitudOrderByVersionDesc(Long idSolicitud);
}
