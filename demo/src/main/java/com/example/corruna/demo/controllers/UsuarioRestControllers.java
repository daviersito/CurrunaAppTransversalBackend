package com.example.corruna.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.corruna.demo.entities.Usuario;
import com.example.corruna.demo.services.UsuarioServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Controlador para gestionar usuarios y autenticación")
public class UsuarioRestControllers {

    @Autowired
    private UsuarioServices usuarioServices;

    @Operation(summary = "Crear un usuario", description = "Crea un nuevo usuario con la información proporcionada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(
            @Parameter(description = "Objeto JSON con los datos del usuario", required = true)
            @RequestBody Usuario usuario
    ) {
        Usuario nuevoUsuario = usuarioServices.crear(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @Operation(summary = "Obtener usuario por ID", description = "Devuelve los datos de un usuario según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario a buscar", example = "1", required = true)
            @PathVariable Long id
    ) {
        Usuario usuario = usuarioServices.ObtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Listar usuarios", description = "Obtiene una lista completa de usuarios registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioServices.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario a eliminar", example = "1", required = true)
            @PathVariable Long id
    ) {
        usuarioServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @Parameter(description = "ID del usuario a actualizar", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del usuario", required = true)
            @RequestBody Usuario usuarioActualizado
    ) {
        Usuario usuario = usuarioServices.actualizar(id, usuarioActualizado);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Desactivar usuario", description = "Desactiva un usuario sin eliminarlo del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario desactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivar(
            @Parameter(description = "ID del usuario a desactivar", example = "1", required = true)
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(usuarioServices.desactivar(id));
    }

    @Operation(summary = "Login de usuario", description = "Autentica a un usuario con email y contraseña")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
        public ResponseEntity<?> login(
        @Parameter(description = "Email y contraseña del usuario", required = true)
        @RequestBody Map<String, String> credenciales
        ) {
        String email = credenciales.get("email");
        String contra = credenciales.get("contra");

        Usuario usuario = usuarioServices.buscarPorEmail(email);
        if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        // Comparar contraseña usando BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(contra, usuario.getContra())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("id", usuario.getId());
        respuesta.put("nombre", usuario.getNombre());
        respuesta.put("email", usuario.getEmail());
        respuesta.put("rol", usuario.getRol().getNombre());

        return ResponseEntity.ok(respuesta);
        }
}
