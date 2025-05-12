package com.example.desafioBackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Sucursal {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nroSucursal;
    private String nombre;
    @OneToMany(mappedBy = "sucursal", fetch = FetchType.LAZY)
    private List<Clase> clases;
}
