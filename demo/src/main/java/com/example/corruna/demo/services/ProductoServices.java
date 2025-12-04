package com.example.corruna.demo.services;

import java.util.List;

import com.example.corruna.demo.entities.Producto;

public interface ProductoServices {

    Producto crear(Producto producto);
    Producto ObtenerPorId(Long id);
    List<Producto> listarTodas();
    void eliminar(Long id);
    Producto actualizar(Long id, Producto productoActualizo);
    Producto desactivar(Long id);
    Producto save(Producto producto);
}
