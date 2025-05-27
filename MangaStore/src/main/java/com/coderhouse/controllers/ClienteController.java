package com.coderhouse.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.coderhouse.dto.CompraClienteDTO;
import com.coderhouse.models.Cliente;
import com.coderhouse.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operaciones para gestionar Clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Operation(summary = "Obtener todos los clientes")
	@ApiResponse(responseCode = "200", description = "Lista de clientes obtenida con éxito")
	@GetMapping(path = {"/",""})
	public ResponseEntity<List<Cliente>> getAllClientes(){
		try {
			List<Cliente> clientes = clienteService.findAll();
			return ResponseEntity.ok(clientes);
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	@Operation(summary = "Obtener cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "400", description = "ID del cliente inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@GetMapping("/{clienteId}")
	public ResponseEntity<?> getClienteById(@PathVariable Long clienteId){
		if(clienteId == null) {
			return ResponseEntity.badRequest().body("El ID del Manga no puede ser Null");//400
		}
		try {
			Cliente cliente = clienteService.findById(clienteId);
			return ResponseEntity.ok(cliente);  // 200
		}catch(IllegalArgumentException err){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado");  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
		
		
	}
	
	@Operation(summary = "Crear un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado con éxito", content = @Content(schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PostMapping("/create")
	public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
		try {
			Cliente clienteCreado = clienteService.save(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);  //201
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	 @Operation(summary = "Actualizar un cliente por ID")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Cliente actualizado con éxito", content = @Content(schema = @Schema(implementation = Cliente.class))),
	            @ApiResponse(responseCode = "400", description = "ID del cliente inválido", content = @Content),
	            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
	            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PutMapping("/{clienteId}")
	public ResponseEntity<?> updateClienteById(@PathVariable Long clienteId, @RequestBody Cliente clienteActualizado){
		if(clienteId == null) {
			return ResponseEntity.badRequest().body("El ID del Manga no puede ser Null");  //400
		}
		try {
			Cliente cliente = clienteService.update(clienteId, clienteActualizado);
			return ResponseEntity.ok(cliente);  // 200
		}catch(IllegalArgumentException err){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado");  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	 @Operation(summary = "Eliminar un cliente por ID")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "204", description = "Cliente eliminado con éxito", content = @Content),
	            @ApiResponse(responseCode = "400", description = "ID del cliente inválido", content = @Content),
	            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
	            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@DeleteMapping("/clienteId")
	public ResponseEntity<?> deleteClienteById(@PathVariable Long clienteId){
		if(clienteId == null) {
			return ResponseEntity.badRequest().body("El ID del Manga no puede ser Null");  //400
		}
		try {
			clienteService.deleteById(clienteId);
			return ResponseEntity.noContent().build();  // 204
		}catch(IllegalArgumentException err){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado");  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
	
	
	@Operation(summary = "Compra de uno o varios Mangas del Cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description ="Compra realizada con exito.!", content = @Content),
			@ApiResponse(responseCode = "500", description ="Error interno del servidor.!", content = @Content),
			@ApiResponse(responseCode = "400", description ="Error intentar eliminar la categoria.!", content = @Content(mediaType ="aplication/json")),
			@ApiResponse(responseCode = "404", description ="Error al encontrar el Cliente o el Cliente no existe.!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "409", description ="Error al encontrar el Cliente o existe un error interno.!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/comprarr")
	public ResponseEntity<?> compraClienteDeMangas(@RequestBody CompraClienteDTO dto){
		try {
			Cliente cliente = clienteService.compraClienteDeMangas(dto);
			return ResponseEntity.ok(cliente);  // 200
		}catch (IllegalStateException err) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(err.getMessage()); //409
		}catch(IllegalArgumentException err){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado");  // 404
		}catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  //500
		}
	}
}
