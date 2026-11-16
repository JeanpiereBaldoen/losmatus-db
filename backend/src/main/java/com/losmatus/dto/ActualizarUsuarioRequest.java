package com.losmatus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ActualizarUsuarioRequest(
        @Email(message = "El email debe ser valido")
        @Size(max = 150)
        String email,

        Long idRol,

        Boolean activo
) {
}

