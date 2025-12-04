package com.example.corruna.demo.services;

import java.util.List;
import com.example.corruna.demo.entities.Boleta;

public interface BoletaService {

    Boleta crearBoleta(Boleta boleta);

    Boleta obtenerBoleta(Long id);

    List<Boleta> obtenerBoletasPorUsuario(Long usuarioId);
    List<Boleta> obtenerTodasLasBoletas();
}