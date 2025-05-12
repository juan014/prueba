package com.example.desafioBackend.DTO;

public record ReservaDTO(
        int nroReserva,
        Boolean asistencia,
        String fechaReserva,
        ClaseDTO clase
) {
}
