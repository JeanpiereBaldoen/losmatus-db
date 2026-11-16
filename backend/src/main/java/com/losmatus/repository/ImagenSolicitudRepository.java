package com.losmatus.repository;

import com.losmatus.entity.ImagenSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenSolicitudRepository extends JpaRepository<ImagenSolicitud, Long> {
    List<ImagenSolicitud> findBySolicitudId(Long solicitudId);
}
