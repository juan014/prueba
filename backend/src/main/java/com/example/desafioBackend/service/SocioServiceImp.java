package com.example.desafioBackend.service;

import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.entities.ClaseGratis;
import com.example.desafioBackend.entities.Reserva;
import com.example.desafioBackend.entities.Socio;
import com.example.desafioBackend.repositories.ClaseGratisRepository;
import com.example.desafioBackend.repositories.ClaseRepository;
import com.example.desafioBackend.repositories.ReservaRepository;
import com.example.desafioBackend.repositories.SocioRepository;
import com.example.desafioBackend.service.serviceInterface.SocioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service

public class SocioServiceImp implements SocioService {

    private final SocioRepository socioRepository;
    private final ReservaRepository reservaRepository;
    private final ClaseRepository claseRepository;
    private final ClaseGratisRepository claseGratisRepository;

    public SocioServiceImp(SocioRepository socioRepository, ReservaRepository reservaRepository, ClaseRepository claseRepository, ClaseGratisRepository claseGratisRepository) {
        this.socioRepository = socioRepository;
        this.reservaRepository = reservaRepository;
        this.claseRepository = claseRepository;
        this.claseGratisRepository = claseGratisRepository;
    }


    @Override
    public Socio update(Socio entity) {
        if (!socioRepository.existsById(entity.getDni())) {
            throw new EntityNotFoundException("Socio no encontrado con DNI: " + entity.getDni());
        }
        return socioRepository.save(entity);
    }

    @Override
    public List<Reserva> getReservasBySocio(int dni) {
        return reservaRepository.findBySocioDni(dni);
    }

    @Override
    public List<Reserva> getReservasBySocioAndFecha(int dni, String fecha) {
        List <Reserva> reservas = this.reservaRepository.findBySocioDni(dni);
        List <Reserva> reservaFecha = new ArrayList<>();
        for (Reserva reserva : reservas) {
            LocalDate fechaReserva = LocalDate.parse(reserva.getFechaReserva());
            if (!fechaReserva.isBefore(LocalDate.parse(fecha))){
                reservaFecha.add(reserva);
            }
        }
        return reservaFecha;
    }

    @Override
    public void cancelarReserva(int nroReserva, int dni) {
        Reserva reserva = reservaRepository.findReservaByNroReservaAndSocio_Dni(nroReserva, dni);
        if (reserva == null) {
            throw new NoSuchElementException("Reserva no encontrada para el socio.");
        }
        LocalDate hoy = LocalDate.now();
        LocalDate fechaClase = LocalDate.parse(reserva.getClase().getFecha());
        if (fechaClase.isBefore(hoy)) {
            throw new IllegalStateException("No se puede cancelar una reserva de una clase ya pasada.");
        }
        Clase clase = reserva.getClase();
        clase.setInscriptos(clase.getInscriptos() - 1);
        claseRepository.save(clase);
        reservaRepository.delete(reserva);
    }
// tengo q verificar que el socio no se haya inscripto ya a esa a clase para que el socio no tenga 2 inscripciones a la misma clase
    @Override
    public void agregarReserva(int nroClase, int dni) {
        LocalDate hoy = LocalDate.now();
        Socio socio = socioRepository.findById(dni).orElseThrow(() -> new NoSuchElementException("Socio no encontrado: " + dni));
        Clase clase = claseRepository.findById(nroClase).orElseThrow(() -> new NoSuchElementException("Clase no encontrada: " + nroClase));
        List<Reserva> reservas = reservaRepository.findBySocioDni(dni);
        for (Reserva reserva : reservas) {
            if (reserva.getClase().getNroClase() == nroClase){
                throw new IllegalStateException("El socio ya esta inscripto a esta clase");
            }
        }
        LocalDate fecha = LocalDate.parse(clase.getFecha());
        if (!fecha.isBefore(hoy) && clase.getInscriptos() < clase.getCapacidad()){
            Reserva reserva = new Reserva();
            reserva.setFechaReserva(String.valueOf(hoy));
            reserva.setSocio(socio);
            reserva.setClase(clase);
            reservaRepository.save(reserva);
            int inscriptos = clase.getInscriptos() + 1;
            clase.setInscriptos(inscriptos);
            claseRepository.save(clase);
        }
        else {
            String motivo = fecha.isBefore(hoy)
                    ? "La clase ya ha ocurrido."
                    : "La clase ya está llena.";
            throw new IllegalStateException("No se puede realizar la reserva: " + motivo);
        }
    }

    @Override
    public void asistenciaClaseGratis(int dni,String fecha) {
        Socio socio = socioRepository.findById(dni).orElseThrow(() -> new RuntimeException("Socio no encontrado: " + dni));
        LocalDate fechaL = LocalDate.parse(fecha);
        Month mes = fechaL.getMonth();
        List<Reserva> reservas = reservaRepository.findBySocioDni(dni);
        List<Reserva> reservasMes = reservas.stream()
                        .filter(reserva -> {
                            LocalDate reservaFecha = LocalDate.parse(reserva.getFechaReserva());
                            Month reservaM = reservaFecha.getMonth();
                            return reservaM == mes;
                        }).toList();

        int countAsistencia = 0;
        for (Reserva reserva:reservasMes){
            if (reserva.getAsistencia() == true){
                countAsistencia++;
            }
        }
        List <ClaseGratis> clasesGratis = claseGratisRepository.findBySocioDni(dni);
        boolean usada = false;
        for (ClaseGratis claseGratis:clasesGratis){
            LocalDate fechaClaseGratis = LocalDate.parse(claseGratis.getFecha());
            Month claseGratisMes = fechaClaseGratis.getMonth();
            if (claseGratisMes == mes) {
                usada = true;
            }
        }

        /*
        for (Reserva reserva:reservas){
            LocalDate reservaFecha = LocalDate.parse(reserva.getFechaReserva());
            Month reservaM = reservaFecha.getMonth();
            if (reservaM == mes){
                reservasMes.add(reserva);
            }
        }
        int countAsistencia = 0;
        for (Reserva reserva:reservasMes){
            if (reserva.getAsistencia() == true){
                countAsistencia++;
            }
        }
        */
        if (countAsistencia >= 10 && !usada){
            ClaseGratis claseGratis = new ClaseGratis();
            claseGratis.setFecha(String.valueOf(fechaL));
            claseGratis.setSocio(socio);
            claseGratisRepository.save(claseGratis);
        }

    }

    @Override
    public void usarClaseGratis(int dni) {
        List<ClaseGratis> clasesGratis = claseGratisRepository.findBySocioDniAndUsadoFalse(dni);
        if (clasesGratis.isEmpty()) {
            throw new IllegalStateException("No hay clases gratis disponibles para este socio.");
        } else {
            for (ClaseGratis claseGratis:clasesGratis){
                if (!claseGratis.isUsado()) {
                    claseGratis.setUsado(true);
                    claseGratisRepository.save(claseGratis);
                    return;
                }
            }
        }
    }

    @Override
    public void reservaAsistida(int nroReserva, int dni) {
        Reserva reserva = reservaRepository.findReservaByNroReservaAndSocio_Dni(nroReserva, dni);
        if (reserva == null) {
            throw new NoSuchElementException("Reserva no encontrada para el socio.");
        }
        LocalDate hoy = LocalDate.now();
        LocalDate fechaClase = LocalDate.parse(reserva.getClase().getFecha());
        if (fechaClase.isBefore(hoy)) {
            throw new IllegalStateException("No se puede cancelar una reserva de una clase ya pasada.");
        }
        reserva.setAsistencia(true);
        reservaRepository.save(reserva);
    }

    @Override
    public void reservaInsistida(int nroReserva, int dni) {
        Reserva reserva = reservaRepository.findReservaByNroReservaAndSocio_Dni(nroReserva, dni);
        if (reserva == null) {
            throw new NoSuchElementException("Reserva no encontrada para el socio.");
        }
        LocalDate hoy = LocalDate.now();
        LocalDate fechaClase = LocalDate.parse(reserva.getClase().getFecha());
        if (fechaClase.isBefore(hoy)) {
            throw new IllegalStateException("No se puede cancelar una reserva de una clase ya pasada.");
        }
        reserva.setAsistencia(false);
        reservaRepository.save(reserva);

    }
    /*
        Socio socio = socioRepository.findById(dni).orElseThrow(() -> new NoSuchElementException("Socio no encontrado: " + dni));
        if (socio.getClasesGratis() > 0){
            socio.setClasesGratis(socio.getClasesGratis()-1);
            socioRepository.save(socio);
        }
        else {
            throw new IllegalStateException("No hay clases gratis disponibles para este socio.");
        } */


    @Override
    public Socio add(Socio entity) {
        Optional<Socio> socios = this.socioRepository.findById(entity.getDni());
        if (socios.isPresent()) {
            throw new IllegalStateException("ya hay un socio asociado con este dni.");
        }
        return socioRepository.save(entity);
    }

    @Override
    public Socio delete(Integer integer) {
        Optional<Socio> socioOptional = this.socioRepository.findById(integer);
        socioOptional.ifPresent(socioRepository::delete);
        return socioOptional.orElseThrow();
    }

    @Override
    public List<Socio> getAll() {
        return socioRepository.findAll();
    }


    @Override
    public Socio getById(Integer integer) {
        return socioRepository.findById(integer).orElse(null);
    }


}
