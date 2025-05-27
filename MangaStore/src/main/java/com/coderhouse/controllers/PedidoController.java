package com.coderhouse.controllers;

import com.coderhouse.dto.PedidoRequestDTO;
import com.coderhouse.models.Pedido;
import com.coderhouse.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos de los clientes")
public class PedidoController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Crear un nuevo pedido")
    @PostMapping("/crear")
    public Pedido crearPedido(
        @RequestBody PedidoRequestDTO pedidoDto
    ) {
        return clienteService.crearPedido(pedidoDto);
    }

    @Operation(summary = "Listar todos los pedidos")
    @GetMapping
    public List<Pedido> listarTodos() {
        return clienteService.obtenerTodosLosPedidos();
    }

    @Operation(summary = "Obtener un pedido por su ID")
    @GetMapping("/{id}")
    public Pedido obtenerPorId(
        @Parameter(description = "ID del pedido", example = "1")
        @PathVariable Long id
    ) {
        return clienteService.obtenerPedidoPorId(id);
    }

    @Operation(summary = "Listar pedidos por ID de cliente")
    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> listarPorCliente(
        @Parameter(description = "ID del cliente", example = "1")
        @PathVariable Long clienteId
    ) {
        return clienteService.obtenerPedidosPorCliente(clienteId);
    }
}
