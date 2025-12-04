package com.example.corruna.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.corruna.demo.entities.Rol;
import com.example.corruna.demo.services.RolServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
@RequestMapping("/api/roles")
@Tag(
        name = "Roles",
        description = "Controlador para gestionar roles de usuarios, incluyendo CRUD completo."
)
public class RolRestControllers {

    @Autowired
    private RolServices rolServices;

    // Crear rol
    @Operation(
            summary = "Crear un nuevo rol",
            description = "Crea un rol nuevo con los datos enviados en el cuerpo de la solicitud."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol creado correctamente."),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados.")
    })
    @PostMapping
    public ResponseEntity<Rol> crearRol(
            @RequestBody
            @Parameter(description = "Objeto JSON con los datos del rol", required = true)
            Rol rol
    ) {
        Rol nuevoRol = rolServices.crear(rol);
        return ResponseEntity.ok(nuevoRol);
    }

    // Obtener rol por ID
    @Operation(
            summary = "Obtener un rol por ID",
            description = "Devuelve los datos de un rol específico según su identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol encontrado."),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(
            @Parameter(description = "ID del rol a buscar", example = "1", required = true)
            @PathVariable Long id
    ) {
        Rol rol = rolServices.ObtenerPorId(id);
        return ResponseEntity.ok(rol);
    }

    // Listar roles
    @Operation(
            summary = "Listar todos los roles",
            description = "Obtiene una lista completa de roles registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de roles obtenida correctamente.")
    })
    @GetMapping
    public ResponseEntity<List<Rol>> listarRol() {
        List<Rol> roles = rolServices.listarTodos();
        return ResponseEntity.ok(roles);
    }

    // Eliminar rol
    @Operation(
            summary = "Eliminar un rol por ID",
            description = "Elimina un rol existente según su identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente."),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(
            @Parameter(description = "ID del rol a eliminar", example = "1", required = true)
            @PathVariable Long id
    ) {
        rolServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar rol
    @Operation(
            summary = "Actualizar un rol existente",
            description = "Actualiza los datos de un rol específico con la información enviada en el cuerpo de la solicitud."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente."),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizarRol(
            @Parameter(description = "ID del rol a actualizar", example = "1", required = true)
            @PathVariable Long id,
            @RequestBody
            @Parameter(description = "Datos actualizados del rol", required = true)
            Rol rolActualizado
    ) {
        Rol rol = rolServices.actualizar(id, rolActualizado);
        return ResponseEntity.ok(rol);
    }
}
