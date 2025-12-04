package com.example.corruna.demo.services;

import com.example.corruna.demo.entities.CartItem;

import org.springframework.data.repository.CrudRepository;


public interface CartItemRepository extends CrudRepository<CartItem, Long> { }
