package com.coderhouse.models;


import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Schema(description = "Modelo de las Categorias")
@Table(name = "Categorias")
public class Categoria {
	
	@Schema(description = "ID del Categoria", requiredMode = Schema.RequiredMode.REQUIRED, example ="1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Schema(description = "Nombre de la Categoria", requiredMode = Schema.RequiredMode.REQUIRED, example ="Shonen")
	@Column(name = "Nombre", nullable = false, unique = true)
	private String nombre;
	
	@Schema(description = "Listado de los Mangas pertenecientes a cada Categoria", example ="One Piece, Naruto, etc...")
	@OneToMany(mappedBy = "categoria", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Manga> mangas = new ArrayList<>();
	
	
}
