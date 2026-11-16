package com.losmatus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe ser valido")
        @Size(max = 150)
        String email,

        @NotBlank(message = "La contrasena es obligatoria")
        @Size(min = 6, max = 100, message = "La contrasena debe tener al menos 6 caracteres")
        String password,

        @NotNull(message = "El rol es obligatorio")
        Long idRol,

        Boolean activo
) {
}

