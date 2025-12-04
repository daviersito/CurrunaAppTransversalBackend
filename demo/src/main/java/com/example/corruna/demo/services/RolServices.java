package com.example.corruna.demo.services;

import java.util.List;

import com.example.corruna.demo.entities.Rol;

public interface RolServices {
    
    Rol crear(Rol rol);
    Rol ObtenerPorId(Long id);
    List<Rol> listarTodos();
    void eliminar(Long id);
    Rol actualizar(Long id, Rol rolActualizo);
}
