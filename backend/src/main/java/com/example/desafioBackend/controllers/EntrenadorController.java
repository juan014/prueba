package com.example.desafioBackend.controllers;

import com.example.desafioBackend.DTO.ClaseDTO;
import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.entities.Entrenador;
import com.example.desafioBackend.service.serviceInterface.EntrenadorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenador")

public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Entrenador>> getAll() {
        List<Entrenador> entrenadores = entrenadorService.getAll();
        return ResponseEntity.ok(entrenadores);
    }

    @PostMapping
    public ResponseEntity<Entrenador> add(Entrenador entity) {
        try {
            Entrenador entrenador = entrenadorService.add(entity);
            return ResponseEntity.ok(entrenador);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("update")
    public ResponseEntity<Entrenador> update(Entrenador entity) {
        try {
            Entrenador entrenador = entrenadorService.update(entity);
            return ResponseEntity.ok(entrenador);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Entrenador> delete(@PathVariable int id) {
        try {
            Entrenador entrenador = entrenadorService.delete(id);
            return ResponseEntity.ok(entrenador);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<Entrenador> getById(@PathVariable int id) {
        Entrenador entrenador = entrenadorService.getById(id);
        if (entrenador == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(entrenador);
    }

    @GetMapping("getClaseEntrenadorByDni/{dni}")
    public ResponseEntity<List<ClaseDTO>> getClaseEntrenadorByDni(@PathVariable int dni) {
        try {
            List<Clase> claseList = entrenadorService.getClasesByEntrenador(dni);
            List<ClaseDTO> claseDTOS = claseList.stream()
                    .map(clase-> new ClaseDTO(
                            clase.getNroClase(),
                            clase.getTipoClase().getNombre(),
                            clase.getFecha(),
                            clase.getHora(),
                            clase.getCapacidad(),
                            clase.getInscriptos(),
                            clase.getEntrenador().getNombre(),
                            clase.getSucursal().getNombre()
                    )).toList();
            return ResponseEntity.ok(claseDTOS);
        } catch (NullPointerException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("getClaseEntrenadorByDniAndFecha/{dni}/{fecha}")
    public ResponseEntity<List<ClaseDTO>> getClaseEntrenadorByDniAndFecha(@PathVariable int dni, @PathVariable String fecha){
        try {
            List<Clase> claseList = entrenadorService.getClasesByEntrenadorAndFecha(dni, fecha);
            List<ClaseDTO> claseDTOS = claseList.stream()
                    .map(clase-> new ClaseDTO(
                            clase.getNroClase(),
                            clase.getTipoClase().getNombre(),
                            clase.getFecha(),
                            clase.getHora(),
                            clase.getCapacidad(),
                            clase.getInscriptos(),
                            clase.getEntrenador().getNombre(),
                            clase.getSucursal().getNombre()
                    )).toList();
            return ResponseEntity.ok(claseDTOS);
        } catch (NullPointerException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
