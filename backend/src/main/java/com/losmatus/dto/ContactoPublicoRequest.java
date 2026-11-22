package com.losmatus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactoPublicoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 120)
        String nombre,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener un formato valido")
        @Size(max = 254)
        String correo,

        @Size(max = 120)
        String empresa,

        @Size(max = 60)
        String telefono,

        @Size(max = 120)
        String servicioInteres,

        @NotBlank(message = "El mensaje es obligatorio")
        @Size(max = 3000)
        String mensaje
) {
}
