package com.example.corruna.demo.services;

import java.util.List;

import com.example.corruna.demo.entities.Categoria;

public interface CategoriaServices {
    
    Categoria crear(Categoria categoria);
    Categoria obtenerPorId(Long id);
    List<Categoria> listarTodas();
    void eliminar(Long id);
    Categoria actualizar(Long id, Categoria categoriaActualiza);
}
