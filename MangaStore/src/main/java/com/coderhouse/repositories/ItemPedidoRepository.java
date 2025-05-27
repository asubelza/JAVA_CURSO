package com.coderhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.ItemPedido;


public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
