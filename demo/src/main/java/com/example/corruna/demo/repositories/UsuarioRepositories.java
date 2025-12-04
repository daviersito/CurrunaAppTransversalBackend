package com.example.corruna.demo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.corruna.demo.entities.Usuario;

public interface UsuarioRepositories extends CrudRepository<Usuario, Long>{

    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByEmail(String email);

    boolean existsByNombre(String nombre);
    boolean existsByEmail(String email);

}
