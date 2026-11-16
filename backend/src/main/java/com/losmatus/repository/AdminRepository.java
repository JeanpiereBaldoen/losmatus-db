package com.losmatus.repository;

import com.losmatus.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByIdUsuario(Long idUsuario);
}
