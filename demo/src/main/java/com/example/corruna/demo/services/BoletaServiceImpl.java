package com.example.corruna.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.corruna.demo.entities.Boleta;
import com.example.corruna.demo.repositories.BoletaRepository;

@Service
public class BoletaServiceImpl implements BoletaService {

    private final BoletaRepository boletaRepo;

    public BoletaServiceImpl(BoletaRepository boletaRepo) {
        this.boletaRepo = boletaRepo;
    }

    @Override
    public Boleta crearBoleta(Boleta boleta) {

        // 🔹 1. Calcular subtotal (totalSinIva)
        double subtotal = 0.0;
        if (boleta.getItems() != null) {
            for (var item : boleta.getItems()) {
                subtotal += item.getPrecio() * item.getCantidad();
            }
        }

        // 🔹 2. Calcular IVA (19%)
        double iva = subtotal * 0.19;

        // 🔹 3. Total con IVA
        double totalConIva = subtotal + iva;

        // 🔹 4. Guardar datos en la boleta antes de persistir
        boleta.setTotalSinIva(subtotal);
        boleta.setIva(iva);
        boleta.setTotalConIva(totalConIva);

        // Por compatibilidad con tu frontend actual
        boleta.setTotal(totalConIva);

        return boletaRepo.save(boleta);
    }


    @Override
    public Boleta obtenerBoleta(Long id) {
        return boletaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));
    }

    @Override
    public List<Boleta> obtenerBoletasPorUsuario(Long usuarioId) {
        return boletaRepo.findByUsuarioId(usuarioId);
    }

    @Override
public List<Boleta> obtenerTodasLasBoletas() {
    return boletaRepo.findAll();
}
}