package com.example.desafioBackend.controllers;

import com.example.desafioBackend.DTO.ClaseDTO;
import com.example.desafioBackend.DTO.SucursalDTO;
import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.entities.Sucursal;
import com.example.desafioBackend.entities.TipoClase;
import com.example.desafioBackend.service.serviceInterface.SucursalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/sucursal")

public class SucursalController {
    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<SucursalDTO>> getAll() {
        List<Sucursal> sucursalList = sucursalService.getAll();
        List<SucursalDTO> dto = sucursalList.stream()
                .map(sucursal -> new SucursalDTO(
                                sucursal.getNroSucursal(),
                                sucursal.getNombre()
                        )
                ).toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("getClaseBySucursalAndFecha/{idSuc}/{fecha}")
    public ResponseEntity<List<ClaseDTO>> getClaseBySucursalAndFecha(@PathVariable int idSuc, @PathVariable String fecha) {
        List<Clase> claseList = sucursalService.getClasesBySucursalAndFecha(idSuc, fecha);
        List<ClaseDTO> dto = claseList.stream().
                map(clase -> new ClaseDTO(
                        clase.getNroClase(),
                        clase.getTipoClase().getNombre(),
                        clase.getFecha(),
                        clase.getHora(),
                        clase.getCapacidad(),
                        clase.getInscriptos(),
                        clase.getEntrenador().getNombre(),
                        clase.getSucursal().getNombre()
                )).toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<SucursalDTO> getSucursal(@PathVariable int id) {
        Sucursal sucursal = sucursalService.getById(id);
        if (sucursal == null) return ResponseEntity.notFound().build();
        SucursalDTO dto = new SucursalDTO(
                sucursal.getNroSucursal(),
                sucursal.getNombre()
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("getByNombre/{nombre}")
    public ResponseEntity<SucursalDTO> getByNombre(@PathVariable String nombre) {
        Sucursal sucursal = sucursalService.getByNombre(nombre);
        if (sucursal == null) return ResponseEntity.notFound().build();
        SucursalDTO dto = new SucursalDTO(
                sucursal.getNroSucursal(),
                sucursal.getNombre()
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("getClaseMasSolicitadaFechas/{fecha1}/{fecha2}")
    public ResponseEntity<TipoClase> getClaseMasSolicitadaByFechas(@PathVariable String fecha1, @PathVariable String fecha2) {
        TipoClase tipoClase = sucursalService.getClaseMasSolicitadaByFechas(fecha1, fecha2);
        return ResponseEntity.ok(tipoClase);
    }


    @PostMapping
    public ResponseEntity<Sucursal> add(@RequestBody Sucursal entity) {
        try {
            Sucursal sucursal = sucursalService.add(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(sucursal);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


    @DeleteMapping("delete/{nroSucursal}")
    public ResponseEntity<Sucursal> delete(@PathVariable int nroSucursal) {
        try {
            Sucursal sucursal = sucursalService.delete(nroSucursal);
            return ResponseEntity.ok().body(sucursal);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
