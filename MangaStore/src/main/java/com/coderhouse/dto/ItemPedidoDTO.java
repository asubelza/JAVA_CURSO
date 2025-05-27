package com.coderhouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO para representar un ítem en un pedido")
public class ItemPedidoDTO {

    @Schema(description = "ID del manga que se quiere comprar", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long mangaId;

    @Schema(description = "Cantidad solicitada del manga", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private int cantidad;

}