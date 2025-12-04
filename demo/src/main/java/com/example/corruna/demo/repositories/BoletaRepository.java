package com.example.corruna.demo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.corruna.demo.entities.Boleta;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {

    // Busca todas las boletas de un usuario
    List<Boleta> findByUsuarioId(Long usuarioId);
}