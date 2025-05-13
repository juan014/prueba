package com.example.desafioBackend.service;

import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.entities.Sucursal;
import com.example.desafioBackend.entities.TipoClase;
import com.example.desafioBackend.repositories.ClaseRepository;
import com.example.desafioBackend.repositories.SucursalRepository;
import com.example.desafioBackend.service.serviceInterface.SucursalService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class SucursalServiceImp implements SucursalService {
    private final SucursalRepository sucursalRepository;
    private final ClaseRepository claseRepository;

    public SucursalServiceImp(SucursalRepository sucursalRepository, ClaseRepository claseRepository) {
        this.sucursalRepository = sucursalRepository;
        this.claseRepository = claseRepository;
    }

    @Override
    public Sucursal add(Sucursal entity) {
        return sucursalRepository.save(entity);
    }

    @Override
    public Sucursal delete(Integer integer) {
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(integer);
        sucursalOptional.ifPresent(sucursalRepository::delete);
        return sucursalOptional.orElse(null);
    }

    @Override
    public List<Sucursal> getAll() {
        return sucursalRepository.findAll();
    }


    @Override
    public Sucursal getById(Integer integer) {
        return sucursalRepository.findById(integer).orElse(null);
    }

    @Override
    public List<Clase> getClasesBySucursalAndFecha(int nroSucursal, String fecha) {
        return claseRepository.findBySucursalNroSucursalAndFecha(nroSucursal, fecha);
    }

    @Override
    public Sucursal getByNombre(String nombre) {
        return sucursalRepository.findByNombre(nombre);
    }

    @Override
    public TipoClase getClaseMasSolicitadaByFechas(String fecha1, String fecha2) {
        LocalDate fecha1Date = LocalDate.parse(fecha1);
        LocalDate fecha2Date = LocalDate.parse(fecha2);
        if (fecha1Date.isAfter(fecha2Date)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
        List<Clase> clases = claseRepository.findByFechaBetween(fecha1, fecha2);
        if (clases.isEmpty()) {
            throw new NoSuchElementException("No hay clases en el rango de fechas indicado.");
        }
        Map<TipoClase, Integer> inscriptosPorTipo = new HashMap<>();
        for (Clase clase : clases) {
            TipoClase tipo = clase.getTipoClase();
            int inscriptos = clase.getInscriptos();
            inscriptosPorTipo.put(tipo, inscriptosPorTipo.getOrDefault(tipo, 0) + inscriptos);
        }
        return inscriptosPorTipo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalStateException("No se pudo determinar el tipo más solicitado."))
                .getKey();
        }
    }


