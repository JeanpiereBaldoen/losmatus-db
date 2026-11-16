package com.losmatus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PersonaRequest(
        @NotBlank(message = "El DNI es obligatorio")
        @Size(max = 20)
        String dni,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 30)
        String nombrePersona,

        @Size(max = 30)
        String apePaternoPersona,

        @Size(max = 30)
        String apeMaternoPersona,

        LocalDate fechaNacimiento,

        @Size(max = 255)
        String telefono,

        @Size(max = 255)
        String direccion,

        @Size(max = 255)
        String urlDireccion
) {
}

