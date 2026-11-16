package com.losmatus.repository;

import com.losmatus.entity.UsuarioSedeArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioSedeAreaRepository extends JpaRepository<UsuarioSedeArea, Long> {
    List<UsuarioSedeArea> findByIdUsuario(Long idUsuario);
}
