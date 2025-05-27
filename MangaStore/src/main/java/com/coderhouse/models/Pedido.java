package com.coderhouse.models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Schema(description = "Modelo de Pedidos")
@Table(name = "Pedidos")
public class Pedido {

    @Schema(description = "ID del Pedido", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Cliente que realizó el Pedido")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Schema(description = "Fecha de creación del Pedido")
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Schema(description = "Estado del Pedido", example = "pendiente")
    @Column(nullable = false)
    private String estado;

    @Schema(description = "Lista de ítems del Pedido")
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemPedido> items = new ArrayList<>();
}
