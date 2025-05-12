package com.example.desafioBackend.DTO;

import java.time.LocalDate;

public record ClaseDTO (
        int nroClase,
        String nombreClase,
        String fecha,
        String hora,
        int capacidad,
        int inscriptos,
        String nombreEntrenador,
        String nombreSucursal

) {
}
