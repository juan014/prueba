package com.example.desafioBackend.controllers;

import com.example.desafioBackend.entities.TipoClase;
import com.example.desafioBackend.service.serviceInterface.TipoClaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipoClase")
public class TipoClaseController {
    private final TipoClaseService tipoClaseService;

    public TipoClaseController(TipoClaseService tipoClaseService) {
        this.tipoClaseService = tipoClaseService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<TipoClase>> getAll() {
        List<TipoClase> tipoClases = tipoClaseService.getAll();
        return ResponseEntity.ok(tipoClases);
    }

    @GetMapping("GetById/{id}")
    public ResponseEntity<TipoClase> getById(@PathVariable int id) {
        TipoClase tipoClase = tipoClaseService.getById(id);
        if (tipoClase == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tipoClase);
    }

    @PostMapping
    public ResponseEntity<TipoClase> add(@RequestBody TipoClase entity) {
        try {
            TipoClase tipoClase = tipoClaseService.add(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoClase);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<TipoClase> delete(@PathVariable int id) {
        try {
            TipoClase tipoClase = tipoClaseService.delete(id);
            return ResponseEntity.ok().body(tipoClase);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
