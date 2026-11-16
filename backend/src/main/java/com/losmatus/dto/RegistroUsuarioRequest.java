package com.losmatus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroUsuarioRequest(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe tener un formato valido")
        @Size(max = 150)
        String email,

        @NotBlank(message = "La contrasena es obligatoria")
        @Size(min = 6, max = 100, message = "La contrasena debe tener al menos 6 caracteres")
        String password,

        @NotBlank(message = "El DNI es obligatorio")
        @Size(max = 20)
        String dni,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 30)
        String nombre,

        @Size(max = 30)
        String apellidoPaterno,

        @Size(max = 30)
        String apellidoMaterno,

        @Size(max = 255)
        String telefono,

        @Size(max = 255)
        String direccion
) {
}

