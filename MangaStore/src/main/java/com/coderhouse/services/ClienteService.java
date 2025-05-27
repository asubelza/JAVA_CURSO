package com.coderhouse.services;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coderhouse.dto.CompraClienteDTO;
import com.coderhouse.dto.PedidoRequestDTO;
import com.coderhouse.interfaces.CrudInterface;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.ItemPedido;
import com.coderhouse.models.Manga;
import com.coderhouse.models.Pedido;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.MangaRepository;
import com.coderhouse.repositories.PedidoRepository;
import com.coderhouse.apis.FechaUtil;

import jakarta.transaction.Transactional;

@Service
public class ClienteService implements CrudInterface<Cliente, Long> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private MangaRepository mangaRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	
	@Override
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente findById(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado") );
	}

	@Override
	@Transactional
	public Cliente save(Cliente nuevoCliente) {
		return clienteRepository.save(nuevoCliente);
	}

	@Override
	@Transactional
	public Cliente update(Long id, Cliente clienteActualizado) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado") );
		
		if(clienteActualizado.getNombre() != null && !clienteActualizado.getNombre().isEmpty()) {
		cliente.setNombre(clienteActualizado.getNombre());
		}
		if(clienteActualizado.getApellido() != null && !clienteActualizado.getApellido().isEmpty()) {
		cliente.setApellido(clienteActualizado.getApellido());
		}
		if(clienteActualizado.getDni() != 0) {
			cliente.setDni(clienteActualizado.getDni());
		}
		if(clienteActualizado.getMembrecia() != null && !clienteActualizado.getMembrecia().isEmpty()) {
			cliente.setMembrecia(clienteActualizado.getMembrecia());
		}
		if(clienteActualizado.getEdad() != 0) {
			cliente.setEdad(clienteActualizado.getEdad());
		}
		
		return clienteRepository.save(cliente);
	}

	@Override
	public void deleteById(Long id) {
		if(!clienteRepository.existsById(id)) {
			throw new IllegalArgumentException("Cliente no encontrado");
		}
		clienteRepository.deleteById(id);
	}
	
	@Transactional
	public Cliente compraClienteDeMangas(CompraClienteDTO dto) {
		Cliente cliente = clienteRepository.findById(dto.getClienteId())
				.orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado") );
		for (Long mangaId : dto.getMangaIds()) {
			Manga manga = mangaRepository.findById(mangaId)
					.orElseThrow(() -> new IllegalArgumentException("Manga no encontrado") );
			
			// Verificar si el cliente ya compro ese producto
			
			if(cliente.getMangas().contains(manga)) {
				throw new IllegalStateException("El cliente ya adquirio este producto de ID" + mangaId);
			}
			cliente.getMangas().add(manga);
			manga.getClientes().add(cliente);
			
			mangaRepository.save(manga);
		}
		
		return clienteRepository.save(cliente);
		
	}
	
	
	public Pedido crearPedido(PedidoRequestDTO dto) {
	    Cliente cliente = clienteRepository.findById(dto.getClienteId())
	        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

	    Pedido pedido = new Pedido();
	    pedido.setCliente(cliente);
	    pedido.setFechaCreacion(FechaUtil.obtenerFechaDesdeAPI()); // ⬅️ Usamos la fecha externa
	    pedido.setEstado("pendiente");

	    List<ItemPedido> items = dto.getItems().stream().map(itemDto -> {
	        Manga manga = mangaRepository.findById(itemDto.getMangaId())
	            .orElseThrow(() -> new RuntimeException("Manga no encontrado"));

	        if (manga.getStock() < itemDto.getCantidad()) {
	            throw new IllegalStateException("Stock insuficiente para el manga: " + manga.getNombre());
	        }

	        manga.setStock(manga.getStock() - itemDto.getCantidad());
	        mangaRepository.save(manga);

	        ItemPedido item = new ItemPedido();
	        item.setManga(manga);
	        item.setCantidad(itemDto.getCantidad());
	        item.setPedido(pedido);

	        return item;
	    }).collect(Collectors.toList());

	    pedido.setItems(items);
	    return pedidoRepository.save(pedido);
	}

	
	public List<Pedido> obtenerTodosLosPedidos() {
	    return pedidoRepository.findAll();
	}

	public Pedido obtenerPedidoPorId(Long id) {
	    return pedidoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
	}

	public List<Pedido> obtenerPedidosPorCliente(Long clienteId) {
	    Cliente cliente = clienteRepository.findById(clienteId)
	            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
	    return pedidoRepository.findByCliente(cliente);
	}


}
