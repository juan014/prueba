package com.example.desafioBackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clases_gratis")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClaseGratis {
    @Id
    private int nroClase;
    private String fecha;
    private boolean usado = false;
    @ManyToOne
    @JoinColumn(name = "dni")
    private Socio socio;
}
