package com.losmatus.repository;

import com.losmatus.entity.ConfiguracionSla;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConfiguracionSlaRepository extends JpaRepository<ConfiguracionSla, Long> {
    List<ConfiguracionSla> findByIdUsuario(Long idUsuario);
    Optional<ConfiguracionSla> findByIdUsuarioAndIdSeveridad(Long idUsuario, Long idSeveridad);
}
