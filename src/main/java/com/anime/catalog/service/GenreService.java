package com.anime.catalog.service;

import com.anime.catalog.dto.AnimeResponseDTO;
import com.anime.catalog.model.Anime;
import com.anime.catalog.model.Genre;

import java.util.List;

public interface GenreService {

    Genre createGenre(Genre genre);

    Genre updateGenre(Long genreId, Genre genre);

    void deleteGenre(Long genreId);

    List<Genre> getAllGenres();

    Genre getGenreById(Long genreId);
    List<AnimeResponseDTO> getAnimeByGenre(Long genreId);
}
