package com.example.desafioBackend.repositories;

import com.example.desafioBackend.entities.TipoClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TipoClaseRepository extends JpaRepository<TipoClase, Integer> {
    TipoClase findByNombre(String nombre);
}
