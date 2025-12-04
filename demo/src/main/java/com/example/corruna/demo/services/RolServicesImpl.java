package com.example.corruna.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.corruna.demo.entities.Rol;

import com.example.corruna.demo.repositories.RolRepositories;

@Service
public class RolServicesImpl implements RolServices{

    @Autowired
    private RolRepositories rolRepositories;

    @Override
    public Rol crear(Rol rol) {
        return rolRepositories.save(rol);
    }

    @Override
    public Rol ObtenerPorId(Long id) {
        return rolRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
    }

    @Override
    public List<Rol> listarTodos() {
        return (List<Rol>) rolRepositories.findAll();
    }

    @Override
    public void eliminar(Long id) {
        if (!rolRepositories.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con id: " + id);
        }
        rolRepositories.deleteById(id);
    }

    @Override
    public Rol actualizar(Long id, Rol rolActualizo) {
        Rol existente = ObtenerPorId(id);
        existente.setNombre(rolActualizo.getNombre());
        return rolRepositories.save(existente);
    }
}
