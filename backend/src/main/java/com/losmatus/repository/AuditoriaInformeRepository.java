package com.losmatus.repository;

import com.losmatus.entity.AuditoriaInforme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditoriaInformeRepository extends JpaRepository<AuditoriaInforme, Long> {
    List<AuditoriaInforme> findByIdInformeOrderByFechaCambioDesc(Long idInforme);
}
