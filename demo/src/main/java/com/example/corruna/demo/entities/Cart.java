package com.example.corruna.demo.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonManagedReference
    private Usuario usuario;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    private Double total; // subtotal SIN IVA

    // ===============================
    // CAMPOS CALCULADOS (NO BD)
    // ===============================
    @Transient
    private Double iva;

    @Transient
    private Double totalConIva;

    // ===============================
    // GETTERS Y SETTERS
    // ===============================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    // ===============================
    // GETTER IVA Y TOTAL + IVA
    // ===============================

    public Double getIva() {
        if (total == null) return 0.0;
        return Math.round((total * 0.19) * 100.0) / 100.0;
    }

    public Double getTotalConIva() {
        if (total == null) return 0.0;
        return Math.round((total + getIva()) * 100.0) / 100.0;
    }
}