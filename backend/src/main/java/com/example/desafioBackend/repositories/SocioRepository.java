package com.example.desafioBackend.repositories;

import com.example.desafioBackend.entities.Reserva;
import com.example.desafioBackend.entities.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Integer> {

}
