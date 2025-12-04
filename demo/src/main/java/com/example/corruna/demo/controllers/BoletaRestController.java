package com.example.corruna.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.corruna.demo.entities.Boleta;
import com.example.corruna.demo.services.BoletaService;

@RestController
@RequestMapping("/api/boletas")
@CrossOrigin(origins = "http://localhost:5173")
public class BoletaRestController {

    private final BoletaService boletaService;

    public BoletaRestController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    @PostMapping
    public Boleta crearBoleta(@RequestBody Boleta boleta) {
        return boletaService.crearBoleta(boleta);
    }

    @GetMapping("/{id}")
    public Boleta obtenerBoleta(@PathVariable Long id) {
        return boletaService.obtenerBoleta(id);
    }

    // 🔹 NUEVO: Obtener todas las boletas de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Boleta> obtenerBoletasUsuario(@PathVariable Long usuarioId) {
        return boletaService.obtenerBoletasPorUsuario(usuarioId);
    }
    // 🔹 Obtener todas las boletas (solo admin)
    @GetMapping
    public List<Boleta> obtenerTodasLasBoletas() {
    return boletaService.obtenerTodasLasBoletas();
}
}