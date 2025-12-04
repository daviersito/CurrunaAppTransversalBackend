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

import com.example.corruna.demo.entities.Categoria;
import com.example.corruna.demo.services.CategoriaServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/categorias")
@Tag(
        name = "Categorías",
        description = "Controlador que gestiona las operaciones CRUD relacionadas con las categorías de productos."
)
public class CategoriaRestController {

    @Autowired
    private CategoriaServices categoriaServices;

    // Crear categoría
    @Operation(
            summary = "Crear una nueva categoría",
            description = "Crea una nueva categoría con los datos enviados en el cuerpo de la solicitud."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría creada correctamente."),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados.")
    })
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(
            @RequestBody
            @Parameter(description = "Objeto JSON con los datos de la nueva categoría", required = true)
            Categoria categoria
    ) {
        Categoria nuevaCategoria = categoriaServices.crear(categoria);
        return ResponseEntity.ok(nuevaCategoria);
    }

    // Obtener categoría por ID
    @Operation(
            summary = "Obtener una categoría por su ID",
            description = "Devuelve los datos de una categoría específica a partir de su identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada."),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(
            @Parameter(description = "ID de la categoría a buscar", example = "1", required = true)
            @PathVariable Long id
    ) {
        Categoria categoria = categoriaServices.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }

    // Listar todas las categorías
    @Operation(
            summary = "Listar todas las categorías",
            description = "Obtiene una lista de todas las categorías registradas en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente.")
    })
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaServices.listarTodas();
        return ResponseEntity.ok(categorias);
    }

    // Eliminar categoría
    @Operation(
            summary = "Eliminar una categoría por ID",
            description = "Elimina una categoría existente a partir de su identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente."),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(
            @Parameter(description = "ID de la categoría a eliminar", example = "1", required = true)
            @PathVariable Long id
    ) {
        categoriaServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar categoría
    @Operation(
            summary = "Actualizar una categoría existente",
            description = "Actualiza los datos de una categoría específica a partir de su ID y la información enviada en el cuerpo de la solicitud."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente."),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @Parameter(description = "ID de la categoría a actualizar", example = "1", required = true)
            @PathVariable Long id,
            @RequestBody
            @Parameter(description = "Datos actualizados de la categoría", required = true)
            Categoria categoriaActualizada
    ) {
        Categoria categoria = categoriaServices.actualizar(id, categoriaActualizada);
        return ResponseEntity.ok(categoria);
    }
}