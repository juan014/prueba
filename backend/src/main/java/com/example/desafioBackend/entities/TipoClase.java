package com.example.desafioBackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TipoClase")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TipoClase {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nroTipoClase;
    @Column(name = "nombreTipo")
    private String nombre;
}
