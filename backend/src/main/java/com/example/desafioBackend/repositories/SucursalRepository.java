package com.example.desafioBackend.repositories;

import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.entities.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    Sucursal findByNombre(String nombre);
}
