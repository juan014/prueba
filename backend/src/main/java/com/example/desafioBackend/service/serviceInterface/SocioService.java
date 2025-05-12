package com.example.desafioBackend.service.serviceInterface;

import com.example.desafioBackend.entities.Reserva;
import com.example.desafioBackend.entities.Socio;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SocioService extends Service<Socio,Integer> {

    Socio update(Socio entity);

    List<Reserva> getReservasBySocio(int dni);

    List<Reserva> getReservasBySocioAndFecha(int dni, String fecha);

    void cancelarReserva(int nroReserva, int dni);

    void agregarReserva(int nroClase, int dni);

    void asistenciaClaseGratis(int dni, String fecha);

    void usarClaseGratis(int dni);
}
