package com.example.desafioBackend.service;

import com.example.desafioBackend.entities.TipoClase;
import com.example.desafioBackend.repositories.TipoClaseRepository;
import com.example.desafioBackend.service.serviceInterface.TipoClaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TipoClaseServiceImp implements TipoClaseService {
    private final TipoClaseRepository tipoClaseRepository;

    public TipoClaseServiceImp(TipoClaseRepository tipoClaseRepository) {
        this.tipoClaseRepository = tipoClaseRepository;
    }

    @Override
    public TipoClase add(TipoClase entity) {
        TipoClase tipoClase = tipoClaseRepository.findByNombre(entity.getNombre());
        if (tipoClase == null) {
            return tipoClaseRepository.save(entity);
        }
        throw new IllegalStateException("ya hay tipo de clase con ese nombre");
    }

    @Override
    public TipoClase delete(Integer integer) {
        Optional<TipoClase> tipoClaseOptional = tipoClaseRepository.findById(integer);
        tipoClaseOptional.ifPresent(tipoClaseRepository::delete);
        return tipoClaseOptional.orElse(null);
    }

    @Override
    public List<TipoClase> getAll() {
        return tipoClaseRepository.findAll();
    }

    @Override
    public TipoClase getById(Integer integer) {
        return tipoClaseRepository.findById(integer).orElse(null);
    }
}
