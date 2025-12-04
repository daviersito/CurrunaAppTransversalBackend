package com.example.corruna.demo.services;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.corruna.demo.entities.Boleta;
import com.example.corruna.demo.entities.BoletaItem;
import com.example.corruna.demo.entities.Cart;
import com.example.corruna.demo.entities.CartItem;
import com.example.corruna.demo.entities.Producto;
import com.example.corruna.demo.entities.Usuario;
import com.example.corruna.demo.repositories.BoletaResitory;
import com.example.corruna.demo.services.CartItemRepository;
import com.example.corruna.demo.repositories.CartRepository;
import com.example.corruna.demo.repositories.ProductoRepositories;
import com.example.corruna.demo.repositories.UsuarioRepositories;

@Service
public class CartServices {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductoRepositories productoRepository;

    @Autowired
    private UsuarioRepositories usuarioRepositories;

    @Autowired
    private BoletaResitory boletaRepository;

    // ===========================================================
    // OBTENER CARRITO
    // ===========================================================
    public Cart getCartByUser(Long userId) {
        return cartRepository.findByUsuarioId(userId)
                .orElseGet(() -> createCartForUser(userId));
    }

    private Cart createCartForUser(Long userId) {
        Cart cart = new Cart();
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        cart.setUsuario(usuario);
        cart.setTotal(0.0);
        return cartRepository.save(cart);
    }

    // ===========================================================
    // AGREGAR ITEM
    // ===========================================================
    public Cart addItem(Long userId, Long productId, int cantidad) {
        Cart cart = getCartByUser(userId);
        Producto producto = productoRepository.findById(productId).orElseThrow();

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProducto(producto);
        item.setCantidad(cantidad);
        item.setSubtotal(producto.getPrecio().doubleValue() * cantidad);

        cart.getItems().add(item);
        cartItemRepository.save(item);

        calcularTotal(cart);
        return cartRepository.save(cart);
    }

    // ===========================================================
    // ELIMINAR ITEM
    // ===========================================================
    public Cart removeItem(Long userId, Long productId) {
        Cart cart = getCartByUser(userId);

        cart.getItems().removeIf(item -> item.getProducto().getId().equals(productId));
        calcularTotal(cart);

        return cartRepository.save(cart);
    }

    // ===========================================================
    // ACTUALIZAR CANTIDAD
    // ===========================================================
    public Cart updateCantidad(Long userId, Long productId, int cantidad) {
        Cart cart = getCartByUser(userId);

        for (CartItem item : cart.getItems()) {
            if (item.getProducto().getId().equals(productId)) {
                if (cantidad <= 0) {
                    cart.getItems().remove(item);
                    break;
                }
                item.setCantidad(cantidad);
                item.setSubtotal(item.getProducto().getPrecio().doubleValue() * cantidad);
            }
        }

        calcularTotal(cart);
        return cartRepository.save(cart);
    }

    // ===========================================================
    // VACIAR CARRITO
    // ===========================================================
    public void clearCart(Long userId) {
        Cart cart = getCartByUser(userId);
        cart.getItems().clear();
        cart.setTotal(0.0);
        cartRepository.save(cart);
    }

    // ===========================================================
    // CHECKOUT
    // ===========================================================
    public Boleta checkout(Long userId) {

        Cart cart = getCartByUser(userId);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // Obtener usuario real para guardar su nombre
        Usuario usuario = usuarioRepositories.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();

        double IVA_RATE = 0.19;
        double ivaAmount = Math.round((total * IVA_RATE) * 100.0) / 100.0;
        double totalWithIva = Math.round((total + ivaAmount) * 100.0) / 100.0;

        Boleta boleta = new Boleta();
        boleta.setUsuarioId(userId);
        boleta.setNombreUsuario(usuario.getNombre()); // ← GUARDA EL NOMBRE DEL USUARIO

        boleta.setTotalSinIva(total);
        boleta.setIva(ivaAmount);
        boleta.setTotalConIva(totalWithIva);
        boleta.setTotal(totalWithIva);

        ZonedDateTime nowZ = ZonedDateTime.now(ZoneId.of("America/Santiago"));
        boleta.setFecha(nowZ.toLocalDateTime());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        boleta.setFechaLocal(nowZ.format(fmt));

        List<BoletaItem> boletaItems = cart.getItems().stream().map(ci -> {
            BoletaItem bi = new BoletaItem();
            bi.setProductoId(ci.getProducto().getId());
            bi.setCantidad(ci.getCantidad());
            bi.setPrecio(ci.getProducto().getPrecio().doubleValue());
            bi.setNombreProducto(ci.getProducto().getNombre());
            return bi;
        }).toList();

        boleta.setItems(boletaItems);
        boletaRepository.save(boleta);

        cart.getItems().clear();
        cart.setTotal(0.0);
        cartRepository.save(cart);

        return boleta;
    }

    // ===========================================================
    // RE-CALCULAR TOTAL
    // ===========================================================
    private void calcularTotal(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
        cart.setTotal(total);
    }
}