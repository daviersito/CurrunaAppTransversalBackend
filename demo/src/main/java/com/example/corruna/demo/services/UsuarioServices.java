package com.example.corruna.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.corruna.demo.entities.Usuario;

public interface UsuarioServices {
    Optional <Usuario> findByNombre(String nombre);
    Optional <Usuario> findByEmail(String email);
    boolean existsByNombre(String nombre);
    boolean existsByEmail(String email);
    Usuario crear(Usuario usuario);
    Usuario ObtenerPorId(Long id);
    List<Usuario> listarTodos();
    void eliminar(Long id);
    Usuario actualizar(Long id, Usuario usuarioActualizo);
    Usuario desactivar(Long id);
    Usuario buscarPorEmail(String email);
    Usuario getUsuarioById(Long id);
}
