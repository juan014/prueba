package com.example.desafioBackend.DTO;

public record SocioDTO(
        int dni,
        String nombre,
        String apellido,
        String email,
        String telefono
) {
}
