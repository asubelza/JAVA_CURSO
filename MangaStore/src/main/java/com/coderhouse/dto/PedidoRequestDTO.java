package com.coderhouse.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO para crear un nuevo pedido")
public class PedidoRequestDTO {

    @Schema(
        description = "ID del cliente que realiza el pedido",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long clienteId;

    @Schema(
        description = "Lista de ítems incluidos en el pedido",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<ItemPedidoDTO> items;
}

