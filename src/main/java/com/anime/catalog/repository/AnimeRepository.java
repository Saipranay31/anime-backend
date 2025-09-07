package com.anime.catalog.repository;

import com.anime.catalog.model.Anime;
import com.anime.catalog.model.Genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
  List<Anime> findAllByGenresContaining(Genre genre);

    // Optional: search anime by name or synopsis
    List<Anime> findByNameContainingIgnoreCaseOrSynopsisContainingIgnoreCase(String name, String synopsis);
}
