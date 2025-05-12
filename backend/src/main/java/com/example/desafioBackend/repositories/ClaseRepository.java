package com.example.desafioBackend.repositories;

import com.example.desafioBackend.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository

public interface ClaseRepository extends JpaRepository<Clase, Integer> {

    List<Clase> findBySucursalNroSucursalAndFecha(int nroSucursal, String fecha);
    List<Clase> findByFechaBetween(String fechaInicio, String fechaFin);
}
