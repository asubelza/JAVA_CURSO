package com.coderhouse.models;


import java.time.LocalDateTime;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Schema(description = "Modelo de Clientes")
@Table(name = "Clientes")
public class Cliente {
	
	@Schema(description = "ID del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example ="1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Schema(description = "Nombre del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example ="Alfonso")
	@Column(name = "Nombre", nullable = false)
	private String nombre;
	
	@Schema(description = "Apellido del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example ="Subelza")
	@Column(name = "Apellido", nullable = false)
	private String apellido;
	
	@Schema(description = "Documento de Identidad del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example ="11222333")
	@Column(name = "DNI", nullable = false, unique = true)
	private int dni;
	
	@Schema(description = "Tipo de Membresia del Cliente", example ="Bronce")
	@Column(name = "Membrecia", nullable = false)
	private String membrecia;
	
	@Schema(description = "Edad del Cliente", example ="50")
	@Column(name = "Edad", nullable = false)
	private int edad;
	
	@Schema(description = "Mangas adquiridos por el Cliente", example ="One Piece, Naruto, etc...")
	@ManyToMany(mappedBy = "clientes", fetch = FetchType.EAGER)
	private List<Manga> mangas = new ArrayList<>();
	private LocalDateTime createdAt;
	

}
