package com.example.corruna.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import com.example.corruna.demo.entities.Usuario;
import com.example.corruna.demo.repositories.UsuarioRepositories;

@Service
public class UsuarioServicesImpl implements UsuarioServices{

    @Autowired
    private UsuarioRepositories usuarioRepositories;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario crear(Usuario usuario) {
        if (usuario.getContra() != null && !usuario.getContra().startsWith("$2a$")) {
            usuario.setContra(passwordEncoder.encode(usuario.getContra()));
        }
        return usuarioRepositories.save(usuario);
    }

    @Override
    public Usuario ObtenerPorId(Long id) {
        return usuarioRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Override
    public List<Usuario> listarTodos() {
        return (List<Usuario>) usuarioRepositories.findAll();
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepositories.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepositories.deleteById(id);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuarioActualizo) {
        Usuario existente = ObtenerPorId(id);
        existente.setNombre(usuarioActualizo.getNombre());
        existente.setEmail(usuarioActualizo.getEmail());
        if (usuarioActualizo.getContra() != null && !usuarioActualizo.getContra().startsWith("$2a$")) {
        existente.setContra(passwordEncoder.encode(usuarioActualizo.getContra()));
        }   
        return usuarioRepositories.save(existente);
    }

    @Override
    public Usuario desactivar(Long id) {
        Usuario existente = ObtenerPorId(id);
        existente.setEstado(false);
        return usuarioRepositories.save(existente);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
    return usuarioRepositories.findByEmail(email).orElse(null);
    }

    @Override
    public Optional<Usuario> findByNombre(String nombre) {
        return usuarioRepositories.findByNombre(nombre);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepositories.findByEmail(email);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return usuarioRepositories.findByNombre(nombre).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepositories.findByEmail(email).isPresent();
    }
    @Override
    public Usuario getUsuarioById(Long id) {
        return usuarioRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }
    
}
