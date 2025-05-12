package com.example.desafioBackend.controllers;

import com.example.desafioBackend.DTO.ClaseDTO;
import com.example.desafioBackend.DTO.ReservaDTO;
import com.example.desafioBackend.DTO.SocioDTO;
import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.entities.Reserva;
import com.example.desafioBackend.entities.Socio;
import com.example.desafioBackend.service.serviceInterface.SocioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/socio")

public class SocioController {
    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<SocioDTO>> getAll(){
        List<Socio> socioList = socioService.getAll();
        List<SocioDTO> dto = socioList.stream()
                .map(socio -> new SocioDTO(
                        socio.getDni(),
                        socio.getNombre(),
                        socio.getApellido(),
                        socio.getEmail(),
                        socio.getTelefono())
                ).toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<SocioDTO> getById(@PathVariable int id){
        Socio socio = socioService.getById(id);
        if (socio == null) return ResponseEntity.notFound().build();
        SocioDTO dto = new SocioDTO(
                socio.getDni(),
                socio.getNombre(),
                socio.getApellido(),
                socio.getEmail(),
                socio.getTelefono()
        );
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        //return socio != null ? ResponseEntity.ok(socio) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Socio> add(@RequestBody Socio entity){
        try {
            Socio socio = socioService.add(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(socio);
        }
        catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Socio> delete(@PathVariable int id){
        try {
            Socio socio = socioService.delete(id);
            return ResponseEntity.ok().body(socio);
        }   catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Socio entity){
        try {
            socioService.update(entity);
            return ResponseEntity.ok().build();
        }   catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("getReservaBySocio/{id}")
    public ResponseEntity<List<ReservaDTO>> getReservasBySocio(@PathVariable int id) {
        List<Reserva> reservas = socioService.getReservasBySocio(id);

        List<ReservaDTO> reservaDTOS = reservas.stream()
                .map(reserva -> {
                    Clase clase = reserva.getClase();
                    ClaseDTO claseDTO = new ClaseDTO(
                            clase.getNroClase(),
                            clase.getTipoClase().getNombre(),
                            clase.getFecha(),
                            clase.getHora(),
                            clase.getCapacidad(),
                            clase.getInscriptos(),
                            clase.getEntrenador().getNombre(),
                            clase.getSucursal().getNombre()

                    );
                    return new ReservaDTO(
                            reserva.getNroReserva(),
                            reserva.getAsistencia(),
                            reserva.getFechaReserva(),
                            claseDTO
                    );
                }).toList();

        return ResponseEntity.ok(reservaDTOS);
    }

    @GetMapping("getReservaBySocioAndFecha/{id}/{fecha}")
    public ResponseEntity<List<ReservaDTO>> getReservaBySocioAndFecha(@PathVariable int id, @PathVariable String fecha){
        List<Reserva> reservas = socioService.getReservasBySocioAndFecha(id, fecha);
        List<ReservaDTO> reservaDTOS = reservas.stream()
                .map(reserva -> {
                    Clase clase = reserva.getClase();
                    ClaseDTO claseDTO = new ClaseDTO(
                            clase.getNroClase(),
                            clase.getTipoClase().getNombre(),
                            clase.getFecha(),
                            clase.getHora(),
                            clase.getCapacidad(),
                            clase.getInscriptos(),
                            clase.getEntrenador().getNombre(),
                            clase.getSucursal().getNombre()
                    );
                    return new ReservaDTO(
                            reserva.getNroReserva(),
                            reserva.getAsistencia(),
                            reserva.getFechaReserva(),
                            claseDTO
                    );
                }).toList();

        return ResponseEntity.ok(reservaDTOS);
    }

    @DeleteMapping("cancelarReserva/{id}/{nroReserva}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable int id, @PathVariable int nroReserva){
        try {
            socioService.cancelarReserva(nroReserva, id);
            return ResponseEntity.ok().build();
        }   catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }   catch (IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("agregarReserva/{nroClase}/{dni}")
    public ResponseEntity<Void> agregarReserva(@PathVariable int nroClase, @PathVariable int dni){
        try {
            socioService.agregarReserva(nroClase, dni);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }  catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("claseGratis/{dni}/{fecha}")
    public ResponseEntity<Void> asistenciaClaseGratis(@PathVariable int dni, @PathVariable String fecha){
        try {
            socioService.asistenciaClaseGratis(dni, fecha);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("usarClaseGratis/{dni}")
    public ResponseEntity<Void> usarClaseGratis(@PathVariable int dni){
        try {
            socioService.usarClaseGratis(dni);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
