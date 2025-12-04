package com.example.corruna.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.corruna.demo.entities.Producto;
import com.example.corruna.demo.repositories.ProductoRepositories;

@Service
public class ProductoServicesImpl implements ProductoServices{

    @Autowired
    private ProductoRepositories productoRepositories;

    @Override
    public Producto crear(Producto producto) {
        return productoRepositories.save(producto);
    }

    @Override
    public Producto ObtenerPorId(Long id) {
        return productoRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    @Override
    public List<Producto> listarTodas() {
        return (List<Producto>) productoRepositories.findAll();
    }

    @Override
    public void eliminar(Long id) {
        if (!productoRepositories.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productoRepositories.deleteById(id);
    }

    @Override
    public Producto actualizar(Long id, Producto productoActualizo) {
        Producto existente = ObtenerPorId(id);
        existente.setDescripcion(productoActualizo.getDescripcion());
        existente.setPrecio(productoActualizo.getPrecio());
        existente.setStock(productoActualizo.getStock());
        existente.setImagen(productoActualizo.getImagen());
        return productoRepositories.save(existente);
    }

    @Override
    public Producto desactivar(Long id) {
        Producto existente = ObtenerPorId(id);
        existente.setEstado(false);
        return productoRepositories.save(existente);
    }
    @Override
    public Producto save(Producto producto) {
        return productoRepositories.save(producto);
    }
}
