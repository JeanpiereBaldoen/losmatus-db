package com.losmatus.repository;

import com.losmatus.entity.ChatIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatIncidenciaRepository extends JpaRepository<ChatIncidencia, Long> {
    List<ChatIncidencia> findByIdIncidenciaOrderByFechaEnvioAsc(Long idIncidencia);
}
