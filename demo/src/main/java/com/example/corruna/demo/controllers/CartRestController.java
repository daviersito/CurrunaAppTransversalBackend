package com.example.corruna.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.corruna.demo.entities.Boleta;
import com.example.corruna.demo.entities.Cart;
import com.example.corruna.demo.services.CartServices;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartRestController {

    @Autowired
    private CartServices cartService;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCartByUser(userId);
    }

    @PostMapping("/{userId}/add")
    public Cart addItem(
        @PathVariable Long userId,
        @RequestParam Long productId,
        @RequestParam int cantidad
        
    ) {
        return cartService.addItem(userId, productId, cantidad);
    }

    @DeleteMapping("/{userId}/remove")
    public Cart removeItem(
        @PathVariable Long userId,
        @RequestParam Long productId
    ) {
        return cartService.removeItem(userId, productId);
    }

    @PutMapping("/{userId}/update")
    public Cart updateCantidad(
        @PathVariable Long userId,
        @RequestParam Long productId,
        @RequestParam int cantidad
    ) {
        return cartService.updateCantidad(userId, productId, cantidad);
    }

    @DeleteMapping("/{userId}/clear")
    public String clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return "Carrito vaciado";
    }

    @PostMapping("/{userId}/checkout")
    public Boleta checkout(@PathVariable Long userId) {
        return cartService.checkout(userId);
    }
}