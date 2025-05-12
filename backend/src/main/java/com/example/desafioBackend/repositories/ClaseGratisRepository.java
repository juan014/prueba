package com.example.desafioBackend.repositories;

import com.example.desafioBackend.entities.ClaseGratis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaseGratisRepository extends JpaRepository<ClaseGratis, Integer> {
    List<ClaseGratis> findBySocioDni(int dni);
    List<ClaseGratis> findBySocioDniAndUsadoTrue(int dni);
}
