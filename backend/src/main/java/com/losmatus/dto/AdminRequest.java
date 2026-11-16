package com.losmatus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminRequest(
        @NotBlank(message = "El nombre del administrador es obligatorio")
        @Size(max = 30)
        String nombreAdmin,

        @Size(max = 15)
        String telefonoAdmin,

        Long idNivelAdmin,

        @NotNull(message = "El ID de usuario es obligatorio")
        Long idUsuario
) {
}

