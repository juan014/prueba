package com.example.desafioBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nroReserva;
    private String fechaReserva;
    private Boolean asistencia;
    @ManyToOne
    @JoinColumn(name = "nroClase")
    private Clase clase;
    @ManyToOne
    @JoinColumn(name = "dniSocio")
    private Socio socio;

}
