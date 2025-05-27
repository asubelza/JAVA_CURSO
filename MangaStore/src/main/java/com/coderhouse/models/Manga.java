package com.coderhouse.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Schema(description = "Modelo de Mangas")
@Table(name = "Mangas")
public class Manga {

	@Schema(description = "ID del Manga", requiredMode = Schema.RequiredMode.REQUIRED, example ="1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Schema(description = "Nombre del Manga", requiredMode = Schema.RequiredMode.REQUIRED, example ="One Piece")
	@Column(name = "Nombre", nullable = false, unique = true)
	private String nombre;
	
	@Schema(description = "Precio del Manga", requiredMode = Schema.RequiredMode.REQUIRED, example ="1000")
	@Column(name = "Precio", nullable = false)
	private Long precio;
	
	@Schema(description = "Stock del Manga", requiredMode = Schema.RequiredMode.REQUIRED, example ="800")
	@Column(name = "Stock", nullable = false)
	private Long stock;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
				name = "manga_cliente",
				joinColumns = @JoinColumn(name = "manga_id"),
				inverseJoinColumns = @JoinColumn(name = "cliente_id")
			)
	
	@JsonIgnore
	private List<Cliente> clientes = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.EAGER)	
	private Categoria categoria;
	
		
}
