package com.example.desafioBackend;

import com.example.desafioBackend.repositories.ClaseRepository;
import com.example.desafioBackend.repositories.SucursalRepository;
import com.example.desafioBackend.service.SucursalServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SucursalServiceImpTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private ClaseRepository claseRepository;

    @InjectMocks
    private SucursalServiceImp sucursalService;

    @Test
    void testGetClaseMasSolicitadaByFechasConFechasInvertidas() {
        String fechaInicio = "2025-05-02";
        String fechaFin = "2025-04-30";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                sucursalService.getClaseMasSolicitadaByFechas(fechaInicio, fechaFin));

        assertEquals("La fecha de inicio no puede ser posterior a la fecha de fin.", ex.getMessage());
    }
}