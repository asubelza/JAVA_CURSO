package com.coderhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.Manga;

public interface MangaRepository extends JpaRepository<Manga, Long> {

}
