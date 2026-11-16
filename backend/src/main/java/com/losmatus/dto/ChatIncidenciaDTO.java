package com.losmatus.dto;

import java.time.LocalDateTime;

public record ChatIncidenciaDTO(
        Long idChat,
        String mensaje,
        LocalDateTime fechaEnvio,
        Long idUsuario,
        String emailUsuario,
        Long idIncidencia
) {
}

