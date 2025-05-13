package com.example.desafioBackend.controllers;

import com.example.desafioBackend.DTO.ClaseDTO;
import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.service.serviceInterface.ClaseService;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clase")

public class ClaseController {
    private final ClaseService claseService;

    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<ClaseDTO>> getAll() {
        List<Clase> clases = claseService.getAll();
        List<ClaseDTO> claseDTOS = clases.stream()
                .map(clase -> {
                    return new ClaseDTO(
                            clase.getNroClase(),
                            clase.getTipoClase().getNombre(),
                            clase.getFecha(),
                            clase.getHora(),
                            clase.getCapacidad(),
                            clase.getInscriptos(),
                            clase.getEntrenador().getNombre(),
                            clase.getSucursal().getNombre()
                    );
                }).toList();
        return ResponseEntity.ok(claseDTOS);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<ClaseDTO> getById(@PathVariable int id) {
        Clase clase = claseService.getById(id);
        if (clase == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        ClaseDTO dto = new ClaseDTO(clase.getNroClase(),
                clase.getTipoClase().getNombre(),
                clase.getFecha(),
                clase.getHora(),
                clase.getCapacidad(),
                clase.getInscriptos(),
                clase.getEntrenador().getNombre(),
                clase.getSucursal().getNombre());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    ResponseEntity<Clase> add(@RequestBody Clase entity) {
        try {
            Clase clase = claseService.add(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(clase);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity<Clase> delete(@PathVariable int id) {
        try {
            Clase clase = claseService.delete(id);
            return ResponseEntity.ok().body(clase);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
