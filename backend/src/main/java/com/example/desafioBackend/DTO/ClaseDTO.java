package com.example.desafioBackend.DTO;

import java.time.LocalDate;

public record ClaseDTO (
        int nroClase,
        int tipoClase,
        String fecha,
        String hora,
        int capacidad,
        int inscriptos,
        int dniEntrenador
) {
}
