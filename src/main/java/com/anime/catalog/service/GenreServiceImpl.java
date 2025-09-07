package com.anime.catalog.service;

import com.anime.catalog.dto.AnimeResponseDTO;
import com.anime.catalog.model.Anime;
import com.anime.catalog.model.Genre;
import com.anime.catalog.repository.AnimeRepository;
import com.anime.catalog.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
   private final AnimeRepository animeRepository;
    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre updateGenre(Long genreId, Genre genre) {
        Genre existing = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        existing.setName(genre.getName());
        return genreRepository.save(existing);
    }

    @Override
    public void deleteGenre(Long genreId) {
        genreRepository.deleteById(genreId);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found"));

    }
   @Override
public List<AnimeResponseDTO> getAnimeByGenre(Long genreId) {
    Genre genre = genreRepository.findById(genreId)
            .orElseThrow(() -> new RuntimeException("Genre not found"));

    List<Anime> animeList = animeRepository.findAllByGenresContaining(genre);

    // Map each Anime -> AnimeResponseDTO
    return animeList.stream().map(anime -> {
        AnimeResponseDTO dto = new AnimeResponseDTO();
        dto.setId(anime.getId());
        dto.setName(anime.getName());
        dto.setSynopsis(anime.getSynopsis());
        dto.setNumberOfSeasons(anime.getNumberOfSeasons());
        dto.setPosterUrl(anime.getPosterUrl());
        if (anime.getGenres() != null) {
            dto.setGenres(anime.getGenres().stream().map(g -> g.getName()).collect(Collectors.toList()));
        }
        return dto;
    }).collect(Collectors.toList());
}
}
