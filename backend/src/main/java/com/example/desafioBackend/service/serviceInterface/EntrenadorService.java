package com.example.desafioBackend.service.serviceInterface;

import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.entities.Entrenador;

import java.util.List;

public interface EntrenadorService extends Service<Entrenador,Integer> {
    List<Clase> getClasesByEntrenador(int dni);
    List<Clase> getClasesByEntrenadorAndFecha(int dni, String fecha);
    Entrenador update(Entrenador entity);
}
