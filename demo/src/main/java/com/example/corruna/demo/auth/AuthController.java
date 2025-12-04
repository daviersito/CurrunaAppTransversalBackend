package com.example.corruna.demo.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService uds;

    public AuthController(AuthenticationManager am,
                          UserDetailsService uds) {
        this.authManager = am;
        this.uds = uds;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {

        // Autentica credenciales con AuthenticationManager (sin generar token)
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.contra())
        );

        UserDetails user = uds.loadUserByUsername(req.email());

        return ResponseEntity.ok(new AuthResponse("Authenticated: " + user.getUsername()));
    }
}
