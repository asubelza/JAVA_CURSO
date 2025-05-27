package com.coderhouse.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coderhouse.interfaces.CrudInterface;
import com.coderhouse.models.Categoria;
import com.coderhouse.models.Manga;
import com.coderhouse.repositories.CategoriaRepository;
import com.coderhouse.repositories.MangaRepository;
import jakarta.transaction.Transactional;

@Service
public class MangaService implements CrudInterface<Manga, Long> {

	@Autowired
	private MangaRepository mangaRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public List<Manga> findAll() {
		return mangaRepository.findAll();
	}

	@Override
	public Manga findById(Long id) {
		return mangaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Manga no encontrado") );
	}

	@Override
	@Transactional
	public Manga save(Manga nuevoManga) {
		return mangaRepository.save(nuevoManga);
	}

	@Override
	@Transactional
	public Manga update(Long id, Manga mangaActualizado) {
		Manga manga = mangaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Manga no encontrado") );
		
		if(mangaActualizado.getNombre() != null && !mangaActualizado.getNombre().isEmpty()) {
		manga.setNombre(mangaActualizado.getNombre());
		}
		
		return mangaRepository.save(manga);
	}

	@Override
	public void deleteById(Long id) {
		if(!mangaRepository.existsById(id)) {
			throw new IllegalArgumentException("Manga no encontrado");
		}
		mangaRepository.deleteById(id);
	}
	
	@Transactional
	public Manga asignarCetegoriaAManga(Long mangaId, Long categoriaId) {
		Categoria categoria = categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new IllegalArgumentException("Categoria inexistente.!"));
		Manga manga = mangaRepository.findById(mangaId)
				.orElseThrow(() -> new IllegalArgumentException("Manga no encontrado") );
		
		//Validar que el manga ya esta clasificado en una Categoria.-
		if(manga.getCategoria() != null && manga.getCategoria().getId().equals(categoriaId)) {
			throw new IllegalStateException("El curso ya tiene esta categoria asignada");
		}
		manga.setCategoria(categoria);
	
	return mangaRepository.save(manga);
	}

}
