package com.example.desafioBackend;

import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.entities.Reserva;
import com.example.desafioBackend.entities.Socio;
import com.example.desafioBackend.repositories.ClaseRepository;
import com.example.desafioBackend.repositories.ReservaRepository;
import com.example.desafioBackend.repositories.SocioRepository;
import com.example.desafioBackend.service.SocioServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocioServiceImpTest {

    @Mock
    private SocioRepository socioRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClaseRepository claseRepository;

    @InjectMocks
    private SocioServiceImp socioServiceImp;

    @Test
    void testAddSocioCuandoDniYaExiste() {
        Socio socio = new Socio();
        socio.setDni(123);
        when(socioRepository.findById(123)).thenReturn(Optional.of(socio));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> socioServiceImp.add(socio));
        assertEquals("ya hay un socio asociado con este dni.", ex.getMessage());
    }

    @Test
    void testAgregarReservaCuandoYaReservoLaClase() {
        int dni = 123;
        int nroClase = 10;
        Socio socio = new Socio();
        socio.setDni(dni);

        Clase clase = new Clase();
        clase.setNroClase(nroClase);
        clase.setFecha(LocalDate.now().plusDays(1).toString());
        clase.setInscriptos(5);
        clase.setCapacidad(30);

        Reserva reservaExistente = new Reserva();
        reservaExistente.setClase(clase);

        when(socioRepository.findById(dni)).thenReturn(Optional.of(socio));
        when(claseRepository.findById(nroClase)).thenReturn(Optional.of(clase));
        when(reservaRepository.findBySocioDni(dni)).thenReturn(List.of(reservaExistente));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> socioServiceImp.agregarReserva(nroClase, dni));

        assertEquals("El socio ya esta inscripto a esta clase", ex.getMessage());
    }

    @Test
    void testAgregarReservaCuandoClaseYaPaso() {
        int dni = 123;
        int nroClase = 10;
        Socio socio = new Socio();
        socio.setDni(dni);

        Clase clase = new Clase();
        clase.setNroClase(nroClase);
        clase.setFecha(LocalDate.now().minusDays(1).toString());
        clase.setInscriptos(5);
        clase.setCapacidad(30);

        when(socioRepository.findById(dni)).thenReturn(Optional.of(socio));
        when(claseRepository.findById(nroClase)).thenReturn(Optional.of(clase));
        when(reservaRepository.findBySocioDni(dni)).thenReturn(List.of());

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> socioServiceImp.agregarReserva(nroClase, dni));

        assertTrue(ex.getMessage().contains("La clase ya ha ocurrido"));
    }

    @Test
    void testCancelarReservaCuandoClaseYaPaso() {
        int dni = 123;
        int nroReserva = 1;

        Clase clase = new Clase();
        clase.setFecha(LocalDate.now().minusDays(1).toString());
        clase.setInscriptos(10);

        Reserva reserva = new Reserva();
        reserva.setClase(clase);

        when(reservaRepository.findReservaByNroReservaAndSocio_Dni(nroReserva, dni))
                .thenReturn(reserva);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> socioServiceImp.cancelarReserva(nroReserva, dni));

        assertEquals("No se puede cancelar una reserva de una clase ya pasada.", ex.getMessage());
    }
}