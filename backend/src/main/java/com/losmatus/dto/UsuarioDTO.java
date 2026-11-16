package com.losmatus.dto;

import java.time.LocalDateTime;

public record UsuarioDTO(
        Long idUsuario,
        String email,
        Boolean activo,
        Long idRol,
        String nombreRol,
        LocalDateTime fechaCreacion
) {
}

