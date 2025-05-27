package com.coderhouse.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.dto.AsignacionCategoriaMangaDTO;
import com.coderhouse.models.Manga;
import com.coderhouse.services.MangaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/mangas")
@Tag(name = "Mangas", description = "Operaciones CRUD sobre mangas y asignación de categorías")
public class MangaController {

	@Autowired
	private MangaService mangaService;
	
	@Operation(summary = "Listar todos los mangas")
	@GetMapping(path = {"/",""})
	public ResponseEntity<List<Manga>> getAllManga(){
		try {
			List<Manga> mangas = mangaService.findAll();
			return ResponseEntity.ok(mangas);
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	@Operation(summary = "Obtener manga por ID")
	@GetMapping("/{mangaId}")
	public ResponseEntity<?> getMangaById(@PathVariable Long mangaId){
		if(mangaId == null) {
			return ResponseEntity.badRequest().body("El ID del Manga no puede ser Null");  //400
		}
		try {
			Manga manga = mangaService.findById(mangaId);
			return ResponseEntity.ok(manga);  // 200
		}catch(IllegalArgumentException err){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado");  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
		
	}
	
	 @Operation(summary = "Crear un nuevo manga")
	@PostMapping("/create")
	public ResponseEntity<Manga> createManga(@RequestBody Manga manga) {
		try {
			Manga mangaCreado = mangaService.save(manga);
			return ResponseEntity.status(HttpStatus.CREATED).body(mangaCreado);  //201
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	@Operation(summary = "Actualizar un manga existente por ID")
	@PutMapping("/{mangaId}")
	public ResponseEntity<?> updateMangaById(@PathVariable Long mangaId, @RequestBody Manga mangaActualizado){
		if(mangaId == null) {
			return ResponseEntity.badRequest().body("El ID del Manga no puede ser Null");  //400
		}
		
		try {
			Manga manga = mangaService.update(mangaId, mangaActualizado);
			return ResponseEntity.ok(manga);  // 200
		}catch(IllegalArgumentException err){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado");  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	@Operation(summary = "Eliminar un manga por ID")
	@DeleteMapping("/{mangaId}")
	public ResponseEntity<?> deleteMangaById(@PathVariable Long mangaId){
	
		if(mangaId == null) {
			return ResponseEntity.badRequest().body("El ID del Manga no puede ser Null");  //400
		}
		try {
			mangaService.deleteById(mangaId);
			return ResponseEntity.noContent().build();  // 204
		}catch(IllegalArgumentException err){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado");  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	@Operation(summary = "Asignar una categoría a un manga")
	@PostMapping("/asignar-categoria")
	public ResponseEntity<?> asignarCategoriaAManga(@RequestBody AsignacionCategoriaMangaDTO dto){
		
		if(dto.getCategoriaId() == null) {
			return ResponseEntity.badRequest().body("El ID de la Categoria no puede ser Null");  //400
		}
		if(dto.getMangaId() == null) {
			return ResponseEntity.badRequest().body("El ID del Manga no puede ser Null");  //400
		}
		try {
			Manga mangaActualizado = mangaService.asignarCetegoriaAManga(
					dto.getMangaId(),
					dto.getCategoriaId()
					);
			return ResponseEntity.ok(mangaActualizado);  //200
		}catch(IllegalStateException err) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(err.getMessage());  //409
		}catch(IllegalArgumentException err){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado");  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
}
