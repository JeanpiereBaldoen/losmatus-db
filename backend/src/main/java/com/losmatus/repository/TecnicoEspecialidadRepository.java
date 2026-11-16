package com.losmatus.repository;

import com.losmatus.entity.TecnicoEspecialidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TecnicoEspecialidadRepository extends JpaRepository<TecnicoEspecialidad, Long> {
    List<TecnicoEspecialidad> findByIdTecnico(Long idTecnico);
}
