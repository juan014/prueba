package com.example.desafioBackend.repositories;

import com.example.desafioBackend.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findBySocioDni(int dni);

    Reserva findReservaByNroReservaAndSocio_Dni(int nroReserva, int dni);

    List<Reserva> findReservaBySocio_DniAndAsistenciaTrue(int socioDni);
}
