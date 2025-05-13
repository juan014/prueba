package com.example.desafioBackend.service;

import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.repositories.ClaseRepository;
import com.example.desafioBackend.service.serviceInterface.ClaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ClaseServiceImp implements ClaseService {
    private final ClaseRepository claseRepository;

    public ClaseServiceImp(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }
// de alguna forma tengo q validar que el entrenador no este en ese mismo dia y horario dando clases
    @Override
    public Clase add(Clase entity) {
        Clase clase = claseRepository.findByEntrenadorDniAndFechaAndHora(entity.getEntrenador().getDni(), entity.getFecha(), entity.getHora());
        if (entity.getCapacidad() >= 0 && clase == null ) {
            return claseRepository.save(entity);
        }
        else {
            throw new IllegalStateException("No debe exister una clase con capacidad negativa o 0.");
        }
    }

    @Override
    public Clase delete(Integer integer) {
        Optional<Clase> claseOptional = claseRepository.findById(integer);
        claseOptional.ifPresent(claseRepository::delete);
        return claseOptional.orElse(null);
    }

    @Override
    public List<Clase> getAll() {
        return claseRepository.findAll();
    }

    @Override
    public Clase getById(Integer integer) {
        return claseRepository.findById(integer).orElse(null);
    }
}
