package com.losmatus.repository;

import com.losmatus.entity.CostoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CostoSolicitudRepository extends JpaRepository<CostoSolicitud, Long> {
    List<CostoSolicitud> findByIdSolicitud(Long idSolicitud);
}
