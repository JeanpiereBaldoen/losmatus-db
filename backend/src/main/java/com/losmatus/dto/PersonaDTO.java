package com.losmatus.dto;

import java.time.LocalDate;

public record PersonaDTO(
        Long idPersona,
        String dni,
        String nombrePersona,
        String apePaternoPersona,
        String apeMaternoPersona,
        LocalDate fechaNacimiento,
        String telefono,
        String direccion,
        String urlDireccion,
        Long usuarioId
) {
}

