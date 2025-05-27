package com.coderhouse.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "DTO de compra del Cliente sobre uno o varios Mangas")
public class CompraClienteDTO {

	@Schema(description = "ID de la Cliente", example = "1")
	private Long clienteId;
	
	@Schema(description = "IDs de los Mangas a adquirir", example = "{1, 2, ...}")
	private List<Long> mangaIds;  
}
