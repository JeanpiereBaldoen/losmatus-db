package com.losmatus.service;

import com.losmatus.config.SecurityUtils;
import com.losmatus.dto.PersonaDTO;
import com.losmatus.dto.PersonaRequest;
import com.losmatus.entity.Persona;
import com.losmatus.exception.BadRequestException;
import com.losmatus.exception.ForbiddenException;
import com.losmatus.exception.ResourceNotFoundException;
import com.losmatus.repository.PersonaRepository;
import com.losmatus.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;

    public PersonaService(PersonaRepository personaRepository, UsuarioRepository usuarioRepository) {
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<PersonaDTO> listarTodas() {
        return personaRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    @Transactional(readOnly = true)
    public PersonaDTO buscarPorId(Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));

        if (SecurityUtils.isCliente() && !persona.getUsuarioId().equals(SecurityUtils.getCurrentUserId())) {
            throw new ForbiddenException("No tiene permiso para ver los datos de otra persona");
        }

        return convertirADTO(persona);
    }

    @Transactional(readOnly = true)
    public PersonaDTO buscarPorUsuarioId(Long usuarioId) {
        if (SecurityUtils.isCliente() && !usuarioId.equals(SecurityUtils.getCurrentUserId())) {
            throw new ForbiddenException("No tiene permiso para ver los datos de otro usuario");
        }

        return personaRepository.findByUsuarioId(usuarioId)
                .map(this::convertirADTO)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro informacion personal para el usuario ID: " + usuarioId));
    }

    @Transactional
    public PersonaDTO actualizar(Long id, PersonaRequest request) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));

        if (SecurityUtils.isCliente() && !persona.getUsuarioId().equals(SecurityUtils.getCurrentUserId())) {
            throw new ForbiddenException("No tiene permiso para modificar datos de otra persona");
        }

        if (!persona.getDni().equals(request.dni()) && personaRepository.existsByDni(request.dni())) {
            throw new BadRequestException("El DNI ya se encuentra registrado: " + request.dni());
        }

        persona.setDni(request.dni().trim());
        persona.setNombrePersona(request.nombrePersona().trim());
        persona.setApePaternoPersona(request.apePaternoPersona() != null ? request.apePaternoPersona().trim() : null);
        persona.setApeMaternoPersona(request.apeMaternoPersona() != null ? request.apeMaternoPersona().trim() : null);
        persona.setFechaNacimiento(request.fechaNacimiento());
        persona.setTelefono(request.telefono() != null ? request.telefono().trim() : null);
        persona.setDireccion(request.direccion() != null ? request.direccion().trim() : null);
        persona.setUrlDireccion(request.urlDireccion() != null ? request.urlDireccion().trim() : null);

        return convertirADTO(personaRepository.save(persona));
    }

    private PersonaDTO convertirADTO(Persona p) {
        return new PersonaDTO(
                p.getIdPersona(),
                p.getDni(),
                p.getNombrePersona(),
                p.getApePaternoPersona(),
                p.getApeMaternoPersona(),
                p.getFechaNacimiento(),
                p.getTelefono(),
                p.getDireccion(),
                p.getUrlDireccion(),
                p.getUsuarioId()
        );
    }
}

