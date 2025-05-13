package com.example.desafioBackend.repositories;

import com.example.desafioBackend.entities.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EntrenadorRepository extends JpaRepository<Entrenador, Integer> {
}
