package com.example.corruna.demo.security;

import com.example.corruna.demo.repositories.UsuarioRepositories;
import com.example.corruna.demo.entities.Usuario;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositories usuarioRepo;

    public CustomUserDetailsService(UsuarioRepositories usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre()));
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),   
                usuario.getContra(), 
                usuario.isEstado(),
                true,
                true,
                true,
                authorities
        );
    }
}
