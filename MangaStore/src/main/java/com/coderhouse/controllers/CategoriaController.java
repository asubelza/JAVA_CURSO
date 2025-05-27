package com.coderhouse.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coderhouse.models.Categoria;
import com.coderhouse.services.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Gestion de Categoria", description = "Endpoints para gestionar Categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	
	@Operation(summary = "Obtener la lista de todas las Categorias")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description ="Lista de Categorias Obtenida con exito.!", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)) }),
			@ApiResponse(responseCode = "500", description ="Error interno del servidor.!", content = @Content())
	})
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Categoria>> getAllCategoria(){
		try {
			List<Categoria> categoria = categoriaService.findAll();
			return ResponseEntity.ok(categoria);
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	@Operation(summary = "Obtener Categoria por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description ="Categoria buscada obtenida con exito.!", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)) }),
			@ApiResponse(responseCode = "500", description ="Error interno del servidor.!", content = @Content),
			@ApiResponse(responseCode = "404", description ="Error al intentar obtener la Categoria.!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{categoriaId}")
	public ResponseEntity<?> getCategoriaById(@PathVariable Long categoriaId){
		if(categoriaId == null) {
			return ResponseEntity.badRequest().body("El ID del Manga no puede ser Null");  //400
		}
		try {
			Categoria categoria = categoriaService.findById(categoriaId);
			return ResponseEntity.ok(categoria);  // 200
		}catch(IllegalArgumentException err){
			return ResponseEntity.notFound().build();  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
		
	}
	
	@Operation(summary = "Crear una nueva Categoria")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description ="Categoria creada con exito.!", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)) }),
			@ApiResponse(responseCode = "500", description ="Error interno del servidor.!", content = @Content)
	})
	@PostMapping("/create")
	public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
		try {
			Categoria categoriaCreada = categoriaService.save(categoria);
			return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada);  //201
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	@Operation(summary = "Actualiza una Categoria")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description ="Categoria actualizada con exito.!", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)) }),
			@ApiResponse(responseCode = "500", description ="Error interno del servidor.!", content = @Content),
			@ApiResponse(responseCode = "400", description ="Error intentar actualizar la categoria.!", content = @Content(mediaType ="aplication/json")),
			@ApiResponse(responseCode = "404", description ="Error al intentar obtener la Categoria.!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PutMapping("/{categoriaId}")
	public ResponseEntity<?> updateCategoriaById(@PathVariable Long categoriaId, @RequestBody Categoria categoriaActualizada){
		if(categoriaId == null) {
			return ResponseEntity.badRequest().body("El ID del Manga no puede ser Null");  //400
		}
		try {
			Categoria categoria = categoriaService.update(categoriaId, categoriaActualizada);
			return ResponseEntity.ok(categoria);  // 200
		}catch(IllegalArgumentException err){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado");  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	@Operation(summary = "Eliminar una Categoria")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description ="Categoria eliminada con exito.!", content = @Content),
			@ApiResponse(responseCode = "500", description ="Error interno del servidor.!", content = @Content),
			@ApiResponse(responseCode = "400", description ="Error intentar eliminar la categoria.!", content = @Content(mediaType ="aplication/json")),
			@ApiResponse(responseCode = "404", description ="Error al intentar obtener la Categoria.!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/categoriaId")
	public ResponseEntity<?> deleteCategoriaById(@PathVariable Long categoriaId){
		if(categoriaId == null) {
			return ResponseEntity.badRequest().body("El ID del Manga no puede ser Null");  //400
		}
		try {
			categoriaService.deleteById(categoriaId);
			return ResponseEntity.noContent().build();  // 204
		}catch(IllegalArgumentException err){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado");  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
}
