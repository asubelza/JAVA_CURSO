package com.coderhouse.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Schema(description = "Ítems de un Pedido")
@Table(name = "Items_Pedido")
public class ItemPedido {

    @Schema(description = "ID del Ítem", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Pedido asociado")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Schema(description = "Manga incluido en el pedido")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manga_id")
    private Manga manga;

    @Schema(description = "Cantidad solicitada", example = "2")
    @Column(nullable = false)
    private int cantidad;

    @Schema(description = "Precio unitario al momento de la compra", example = "1500")
    @Column(nullable = false)
    private Long precioUnitario;
}
