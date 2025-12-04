package com.example.corruna.demo.repositories;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.corruna.demo.entities.Cart;


public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findByUsuarioId(Long usuarioId);
    


    
}
