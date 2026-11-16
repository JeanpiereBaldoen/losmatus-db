package com.losmatus.repository;

import com.losmatus.entity.HistorialSolicitudServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialSolicitudServicioRepository extends JpaRepository<HistorialSolicitudServicio, Long> {
    List<HistorialSolicitudServicio> findByIdSolicitudOrderByFechaCambioDesc(Long idSolicitud);
}
