package com.example.corruna.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.corruna.demo.entities.Producto;

public interface ProductoRepositories extends CrudRepository<Producto, Long>{

}
