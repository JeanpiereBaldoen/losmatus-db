package com.losmatus.controller;

import com.losmatus.dto.AdminDTO;
import com.losmatus.dto.AdminRequest;
import com.losmatus.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> listar() {
        return ResponseEntity.ok(adminService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AdminDTO> crear(@Valid @RequestBody AdminRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> actualizar(@PathVariable Long id, @Valid @RequestBody AdminRequest request) {
        return ResponseEntity.ok(adminService.actualizar(id, request));
    }
}

