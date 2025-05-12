package com.example.desafioBackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Entrenador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entrenador {
    @Id
    @NotNull
    private int dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}
