package com.losmatus.dto;

public record AdminDTO(
        Long idAdmin,
        String nombreAdmin,
        String telefonoAdmin,
        Long idNivelAdmin,
        String nombreNivelAdmin,
        Long idUsuario,
        String emailUsuario
) {
}

