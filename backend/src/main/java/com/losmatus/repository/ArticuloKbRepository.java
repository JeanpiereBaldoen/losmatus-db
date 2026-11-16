package com.losmatus.repository;

import com.losmatus.entity.ArticuloKb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticuloKbRepository extends JpaRepository<ArticuloKb, Long> {
    List<ArticuloKb> findByEstado(ArticuloKb.Estado estado);
}
