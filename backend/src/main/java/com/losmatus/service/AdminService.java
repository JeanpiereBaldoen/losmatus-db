package com.losmatus.service;

import com.losmatus.dto.AdminDTO;
import com.losmatus.dto.AdminRequest;
import com.losmatus.entity.Admin;
import com.losmatus.entity.NivelAdmin;
import com.losmatus.entity.Usuario;
import com.losmatus.exception.BadRequestException;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.AdminRepository;
import com.losmatus.repository.NivelAdminRepository;
import com.losmatus.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UsuarioRepository usuarioRepository;
    private final NivelAdminRepository nivelAdminRepository;

    public AdminService(AdminRepository adminRepository, UsuarioRepository usuarioRepository, NivelAdminRepository nivelAdminRepository) {
        this.adminRepository = adminRepository;
        this.usuarioRepository = usuarioRepository;
        this.nivelAdminRepository = nivelAdminRepository;
    }

    @Transactional(readOnly = true)
    public List<AdminDTO> listarTodos() {
        return adminRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    @Transactional(readOnly = true)
    public AdminDTO buscarPorId(Long id) {
        return adminRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new ResourceNotFoundException("Admin no encontrado con ID: " + id));
    }

    @Transactional
    public AdminDTO crear(AdminRequest request) {
        if (!usuarioRepository.existsById(request.idUsuario())) {
            throw new BadRequestException("El usuario con ID " + request.idUsuario() + " no existe");
        }

        if (adminRepository.findByIdUsuario(request.idUsuario()).isPresent()) {
            throw new BadRequestException("El usuario ya tiene un registro de administrador asignado");
        }

        if (request.idNivelAdmin() != null && !nivelAdminRepository.existsById(request.idNivelAdmin())) {
            throw new BadRequestException("El nivel de admin con ID " + request.idNivelAdmin() + " no existe");
        }

        Admin admin = new Admin();
        admin.setNombreAdmin(request.nombreAdmin().trim());
        admin.setTelefonoAdmin(request.telefonoAdmin() != null ? request.telefonoAdmin().trim() : null);
        admin.setIdNivelAdmin(request.idNivelAdmin());
        admin.setIdUsuario(request.idUsuario());

        return convertirADTO(adminRepository.save(admin));
    }

    @Transactional
    public AdminDTO actualizar(Long id, AdminRequest request) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin no encontrado con ID: " + id));

        if (request.idNivelAdmin() != null && !nivelAdminRepository.existsById(request.idNivelAdmin())) {
            throw new BadRequestException("El nivel de admin con ID " + request.idNivelAdmin() + " no existe");
        }

        admin.setNombreAdmin(request.nombreAdmin().trim());
        admin.setTelefonoAdmin(request.telefonoAdmin() != null ? request.telefonoAdmin().trim() : null);
        admin.setIdNivelAdmin(request.idNivelAdmin());

        return convertirADTO(adminRepository.save(admin));
    }

    private AdminDTO convertirADTO(Admin a) {
        String nombreNivel = null;
        if (a.getIdNivelAdmin() != null) {
            nombreNivel = nivelAdminRepository.findById(a.getIdNivelAdmin())
                    .map(NivelAdmin::getNombre)
                    .orElse(null);
        }

        String email = usuarioRepository.findById(a.getIdUsuario())
                .map(Usuario::getEmail)
                .orElse(null);

        return new AdminDTO(
                a.getIdAdmin(),
                a.getNombreAdmin(),
                a.getTelefonoAdmin(),
                a.getIdNivelAdmin(),
                nombreNivel,
                a.getIdUsuario(),
                email
        );
    }
}

