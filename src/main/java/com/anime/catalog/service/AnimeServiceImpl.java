package com.anime.catalog.service;

import com.anime.catalog.dto.AnimeRequestDTO;
import com.anime.catalog.dto.AnimeResponseDTO;
import com.anime.catalog.model.Anime;
import com.anime.catalog.model.Genre;

import com.anime.catalog.repository.AnimeRepository;

import com.anime.catalog.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService {

    private final AnimeRepository animeRepository;
  
    private final GenreRepository genreRepository;
@Override
public Anime createAnime(AnimeRequestDTO dto) {
    Anime anime = new Anime();
    anime.setName(dto.getName());
    anime.setSynopsis(dto.getSynopsis());
    anime.setNumberOfSeasons(dto.getNumberOfSeasons());
    anime.setPosterUrl(dto.getPosterUrl());
    // map genre IDs to Genre entities
    List<Genre> genres = dto.getGenreIds().stream()
            .map(id -> genreRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Genre not found: " + id)))
            .collect(Collectors.toList());

    anime.setGenres(genres);
  

    

    return animeRepository.save(anime);
}

@Override
public Anime updateAnime(Long animeId, AnimeRequestDTO dto) {
    Anime anime = animeRepository.findById(animeId)
            .orElseThrow(() -> new RuntimeException("Anime not found"));

    anime.setName(dto.getName());
    anime.setSynopsis(dto.getSynopsis());
    anime.setNumberOfSeasons(dto.getNumberOfSeasons());
    anime.setPosterUrl(dto.getPosterUrl());

    List<Genre> genres = dto.getGenreIds().stream()
            .map(id -> genreRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Genre not found: " + id)))
            .collect(Collectors.toList());

    anime.setGenres(genres);
    


    return animeRepository.save(anime);
}

    @Override
    public void deleteAnime(Long animeId) {
        animeRepository.deleteById(animeId);
    }

    @Override
    public Anime getAnimeById(Long animeId) {
        return animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("Anime not found"));
    }

    @Override
    public List<Anime> getAllAnime() {
        return animeRepository.findAll();
    }


    @Override
public List<AnimeResponseDTO> searchAnime(String query) {
    List<Anime> animeList = animeRepository
            .findByNameContainingIgnoreCaseOrSynopsisContainingIgnoreCase(query, query);

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
@Override
public AnimeResponseDTO getAnimeDtoById(Long animeId) {
    Anime anime = animeRepository.findById(animeId)
            .orElseThrow(() -> new RuntimeException("Anime not found"));

    return mapToResponseDTO(anime);
}

@Override
public List<AnimeResponseDTO> getAllAnimeDto() {
    return animeRepository.findAll()
            .stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
}
private AnimeResponseDTO mapToResponseDTO(Anime anime) {
    AnimeResponseDTO dto = new AnimeResponseDTO();
    dto.setId(anime.getId());
    dto.setName(anime.getName());
    dto.setSynopsis(anime.getSynopsis());
    dto.setNumberOfSeasons(anime.getNumberOfSeasons());
    dto.setPosterUrl(anime.getPosterUrl());

    if (anime.getGenres() != null) {
        dto.setGenres(anime.getGenres()
                .stream()
                .map(g -> g.getName())
                .collect(Collectors.toList()));
    }
    return dto;
}

 
    
}
