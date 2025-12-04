package com.example.corruna.demo.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del usuario que hizo la compra
    private Long usuarioId;

    // Nombre del usuario que hizo la compra
    private String nombreUsuario;

    private LocalDateTime fecha;
    private String fechaLocal;

    private Double total;
    private Double totalSinIva;
    private Double iva;
    private Double totalConIva;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "boleta_id")
    private List<BoletaItem> items;

    // GETTERS Y SETTERS
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getFechaLocal() { return fechaLocal; }
    public void setFechaLocal(String fechaLocal) { this.fechaLocal = fechaLocal; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Double getTotalSinIva() { return totalSinIva; }
    public void setTotalSinIva(Double totalSinIva) { this.totalSinIva = totalSinIva; }

    public Double getIva() { return iva; }
    public void setIva(Double iva) { this.iva = iva; }

    public Double getTotalConIva() { return totalConIva; }
    public void setTotalConIva(Double totalConIva) { this.totalConIva = totalConIva; }

    public List<BoletaItem> getItems() { return items; }
    public void setItems(List<BoletaItem> items) { this.items = items; }
}