package com.example.desafioBackend.service;
import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.entities.Entrenador;
import com.example.desafioBackend.repositories.ClaseRepository;
import com.example.desafioBackend.repositories.EntrenadorRepository;
import com.example.desafioBackend.service.serviceInterface.EntrenadorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class EntrenadorServiceImp implements EntrenadorService {
     private final EntrenadorRepository entrenadorRepository;
     private final ClaseRepository claseRepository;

    public EntrenadorServiceImp(EntrenadorRepository entrenadorRepository, ClaseRepository claseRepository) {
        this.entrenadorRepository = entrenadorRepository;
        this.claseRepository = claseRepository;
    }

    @Override
    public List<Clase> getClasesByEntrenador(int dni) {
        return claseRepository.findByEntrenadorDni(dni);
    }

    @Override
    public List<Clase> getClasesByEntrenadorAndFecha(int dni, String fecha) {
        return claseRepository.findByEntrenadorDniAndFecha(dni, fecha);
    }

    @Override
    public Entrenador update(Entrenador entity) {
        if (!entrenadorRepository.existsById(entity.getDni())) {
            throw new EntityNotFoundException("Entrenador no encontrado con DNI: " + entity.getDni());
        }
        return entrenadorRepository.save(entity);
    }

    @Override
    public Entrenador add(Entrenador entity) {
        Optional<Entrenador> entrenadorOptional = this.entrenadorRepository.findById(entity.getDni());
        if (entrenadorOptional.isPresent()) {
            throw new IllegalStateException("ya hay un socio asociado con este dni.");
        }
        return entrenadorRepository.save(entity);
    }

    @Override
    public Entrenador delete(Integer integer) {
        Optional<Entrenador> entrenadorOptional = entrenadorRepository.findById(integer);
        entrenadorOptional.ifPresent(entrenadorRepository::delete);
        return entrenadorOptional.orElse(null);
    }

    @Override
    public List<Entrenador> getAll() {
        return entrenadorRepository.findAll();
    }

    @Override
    public Entrenador getById(Integer integer) {
        Optional<Entrenador> entrenadorOptional = entrenadorRepository.findById(integer);
        return entrenadorOptional.orElse(null);
    }
}
