package com.losmatus.dto;

public record TecnicoDTO(
        Long idTecnico,
        String nombreTecnico,
        String apePaternoTecnico,
        String apeMaternoTecnico,
        String dniTecnico,
        String telefono,
        Long idEstadoTecnico,
        String nombreEstadoTecnico,
        Long idUsuario,
        String emailUsuario
) {
}

