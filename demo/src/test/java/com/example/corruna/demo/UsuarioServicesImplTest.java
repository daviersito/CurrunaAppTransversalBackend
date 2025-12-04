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

import com.example.corruna.demo.entities.Usuario;
import com.example.corruna.demo.repositories.UsuarioRepositories;
import com.example.corruna.demo.services.UsuarioServicesImpl;

public class UsuarioServicesImplTest {

    @Mock
    private UsuarioRepositories usuarioRepositories;

    @InjectMocks
    private UsuarioServicesImpl usuarioServices;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario1 = new Usuario(1L, "Juan", "juan@mail.com", "1234", true, 20251023, null, null);
        usuario2 = new Usuario(2L, "Ana", "ana@mail.com", "abcd", true, 20251023, null, null);
    }

    @Test
    void testCrearUsuario() {
        when(usuarioRepositories.save(usuario1)).thenReturn(usuario1);

        Usuario resultado = usuarioServices.crear(usuario1);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(usuarioRepositories, times(1)).save(usuario1);
    }

    @Test
    void testObtenerPorId_Existente() {
        when(usuarioRepositories.findById(1L)).thenReturn(Optional.of(usuario1));

        Usuario resultado = usuarioServices.ObtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void testObtenerPorId_NoExistente() {
        when(usuarioRepositories.findById(3L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioServices.ObtenerPorId(3L);
        });

        assertEquals("Usuario no encontrado con id: 3", exception.getMessage());
    }

    @Test
    void testListarTodos() {
        when(usuarioRepositories.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> usuarios = usuarioServices.listarTodos();

        assertEquals(2, usuarios.size());
        verify(usuarioRepositories, times(1)).findAll();
    }

    @Test
    void testDesactivarUsuario() {
        when(usuarioRepositories.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioRepositories.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        Usuario resultado = usuarioServices.desactivar(1L);

        assertFalse(resultado.isEstado());
        verify(usuarioRepositories, times(1)).save(usuario1);
    }

    @Test
    void testBuscarPorEmail_Existente() {
        when(usuarioRepositories.findByEmail("juan@mail.com")).thenReturn(Optional.of(usuario1));

        Usuario resultado = usuarioServices.buscarPorEmail("juan@mail.com");

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void testBuscarPorEmail_NoExistente() {
        when(usuarioRepositories.findByEmail("noexiste@mail.com")).thenReturn(Optional.empty());

        Usuario resultado = usuarioServices.buscarPorEmail("noexiste@mail.com");

        assertNull(resultado);
    }
}
