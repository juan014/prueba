package com.example.desafioBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Clase")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Clase {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nroClase;
    @ManyToOne
    @JoinColumn(name = "nroTipoClase")
    private TipoClase tipoClase;
    private String fecha;
    private String hora;
    private int capacidad;
    private int inscriptos;
    @ManyToOne
    @JoinColumn(name = "dniEntrenador")
    private Entrenador entrenador;
    @ManyToOne
    @JoinColumn(name = "nroSucursal")
    private Sucursal sucursal;
}
