package com.example.corruna.demo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.corruna.demo.entities.Producto;
import com.example.corruna.demo.repositories.ProductoRepositories;
import com.example.corruna.demo.services.ProductoServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/productos")
@Tag(
        name = "Productos",
        description = "Controlador que gestiona las operaciones CRUD de los productos, incluyendo subida de imágenes."
)
public class ProductoRestControllers {

    private final ProductoRepositories productoRepositories;

    @Autowired
    private ProductoServices productoServices;

    public ProductoRestControllers(ProductoRepositories productoRepositories) {
        this.productoRepositories = productoRepositories;
    }

    // Crear producto
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Crea un nuevo producto en el sistema con los datos enviados en el cuerpo de la solicitud."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto creado correctamente."),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados.")
    })
    @PostMapping
    public ResponseEntity<Producto> crearProducto(
            @RequestBody
            @Parameter(description = "Objeto JSON con los datos del nuevo producto", required = true)
            Producto producto
    ) {
        Producto nuevoProducto = productoServices.crear(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    // Obtener producto por ID
    @Operation(
            summary = "Obtener un producto por su ID",
            description = "Devuelve los datos de un producto específico según su identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado."),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(
            @Parameter(description = "ID del producto a buscar", example = "1", required = true)
            @PathVariable Long id
    ) {
        Producto producto = productoServices.ObtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    // Listar productos
    @Operation(
            summary = "Listar todos los productos",
            description = "Obtiene una lista completa de los productos registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente.")
    })
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoServices.listarTodas();
        return ResponseEntity.ok(productos);
    }

    // Eliminar producto
    @Operation(
            summary = "Eliminar un producto por ID",
            description = "Elimina un producto existente según su identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente."),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @Parameter(description = "ID del producto a eliminar", example = "1", required = true)
            @PathVariable Long id
    ) {
        productoServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar producto
    @Operation(
            summary = "Actualizar un producto existente",
            description = "Actualiza los datos de un producto específico con la información enviada en el cuerpo de la solicitud."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente."),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @Parameter(description = "ID del producto a actualizar", example = "1", required = true)
            @PathVariable Long id,
            @RequestBody
            @Parameter(description = "Datos actualizados del producto", required = true)
            Producto productoActualizado
    ) {
        Producto producto = productoServices.actualizar(id, productoActualizado);
        return ResponseEntity.ok(producto);
    }

    // Desactivar producto
    @Operation(
            summary = "Desactivar un producto",
            description = "Cambia el estado de un producto a inactivo sin eliminarlo del sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto desactivado correctamente."),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado.")
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Producto> desactivarProducto(
            @Parameter(description = "ID del producto a desactivar", example = "1", required = true)
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(productoServices.desactivar(id));
    }

    // Subir imagen
    @Operation(
            summary = "Subir una imagen para un producto",
            description = "Permite subir una imagen y devuelve la URL generada para su acceso desde el frontend."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Imagen subida correctamente."),
            @ApiResponse(responseCode = "500", description = "Error al subir la imagen.")
    })
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @Parameter(description = "Archivo de imagen a subir", required = true)
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "/uploads/" + fileName;

            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al subir imagen");
        }
    }
}
