package com.example.corruna.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.corruna.demo.entities.Categoria;
import com.example.corruna.demo.repositories.CategoriaRepositories;

@Service
public class CategoriaServicesImpl implements CategoriaServices {

    @Autowired
    private CategoriaRepositories categoriaRepositories;

    @Override
    public Categoria crear(Categoria categoria) {
        return categoriaRepositories.save(categoria);
    }

    @Override
    public Categoria obtenerPorId(Long id) {
        return categoriaRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + id));
    }

    @Override
    public List<Categoria> listarTodas() {
        return (List<Categoria>) categoriaRepositories.findAll();
    }

    @Override
    public void eliminar(Long id) {
        if (!categoriaRepositories.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada con id: " + id);
        }
        categoriaRepositories.deleteById(id);
    }

    @Override
    public Categoria actualizar(Long id, Categoria categoriaActualiza) {
        Categoria existente = obtenerPorId(id);
        existente.setNombre(categoriaActualiza.getNombre());
        return categoriaRepositories.save(existente);
    }
}
