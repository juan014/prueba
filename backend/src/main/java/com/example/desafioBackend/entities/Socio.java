package com.example.desafioBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Socio")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Socio {
    @Id
    @NotNull
    private int dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    @OneToMany(mappedBy = "socio", fetch = FetchType.LAZY)
    private List<Reserva> reservas;
    private int clasesGratis = 0;



}
