package com.example.desafioBackend.service.serviceInterface;

import com.example.desafioBackend.entities.Clase;
import com.example.desafioBackend.entities.Sucursal;
import com.example.desafioBackend.entities.TipoClase;

import java.time.LocalDate;
import java.util.List;

public interface SucursalService extends Service<Sucursal,Integer> {

    List<Clase> getClasesBySucursalAndFecha(int nroSucursal, String fecha);

    Sucursal getByNombre(String nombre);

    TipoClase getClaseMasSolicitadaByFechas(String fecha1, String fecha2);
}
