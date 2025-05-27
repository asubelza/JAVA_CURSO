package com.coderhouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO de Asignacion de Categoria a los Mangas")
public class AsignacionCategoriaMangaDTO {

	@Schema(description = "ID de la Categoria", example = "1")
	private Long categoriaId;
	
	@Schema(description = "ID del Manga", example = "1")
	private Long mangaId;
}
