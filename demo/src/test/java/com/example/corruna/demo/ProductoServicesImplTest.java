package com.example.corruna.demo;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.corruna.demo.entities.Producto;
import com.example.corruna.demo.repositories.ProductoRepositories;
import com.example.corruna.demo.services.ProductoServicesImpl;

public class ProductoServicesImplTest {

    @Mock
    private ProductoRepositories productoRepositories;

    @InjectMocks
    private ProductoServicesImpl productoServices;

    private Producto producto1;
    private Producto producto2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        producto1 = new Producto(1L, "Arroz", "Arroz integral", 1500L, 10, null, true, 20251023, null);
        producto2 = new Producto(2L, "Azúcar", "Azúcar blanca", 1000L, 3, null, true, 20251023, null);
    }

    @Test
    void testCrearProducto() {
        when(productoRepositories.save(producto1)).thenReturn(producto1);

        Producto resultado = productoServices.crear(producto1);

        assertNotNull(resultado);
        assertEquals("Arroz", resultado.getNombre());
        verify(productoRepositories, times(1)).save(producto1);
    }

    @Test
    void testObtenerPorId_Existente() {
        when(productoRepositories.findById(1L)).thenReturn(Optional.of(producto1));

        Producto resultado = productoServices.ObtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Arroz", resultado.getNombre());
    }

    @Test
    void testObtenerPorId_NoExistente() {
        when(productoRepositories.findById(3L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoServices.ObtenerPorId(3L);
        });

        assertEquals("Producto no encontrado con id: 3", exception.getMessage());
    }

    @Test
    void testListarTodas() {
        when(productoRepositories.findAll()).thenReturn(Arrays.asList(producto1, producto2));

        List<Producto> productos = productoServices.listarTodas();

        assertEquals(2, productos.size());
        verify(productoRepositories, times(1)).findAll();
    }

    @Test
    void testDesactivarProducto() {
        when(productoRepositories.findById(2L)).thenReturn(Optional.of(producto2));
        when(productoRepositories.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        Producto resultado = productoServices.desactivar(2L);

        assertFalse(resultado.isEstado());
        verify(productoRepositories, times(1)).save(producto2);
    }

    @Test
    void testActualizarProducto() {
        when(productoRepositories.findById(1L)).thenReturn(Optional.of(producto1));
        when(productoRepositories.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        Producto actualizado = new Producto();
        actualizado.setDescripcion("Arroz integral premium");
        actualizado.setPrecio(2000L);
        actualizado.setStock(15);

        Producto resultado = productoServices.actualizar(1L, actualizado);

        assertEquals("Arroz integral premium", resultado.getDescripcion());
        assertEquals(2000L, resultado.getPrecio());
        assertEquals(15, resultado.getStock());
        verify(productoRepositories, times(1)).save(producto1);
    }
}
