package com.anime.catalog.service;

import com.anime.catalog.dto.AnimeRequestDTO;
import com.anime.catalog.dto.AnimeResponseDTO;
import com.anime.catalog.model.Anime;


import java.util.List;

public interface AnimeService {

    Anime createAnime(AnimeRequestDTO animeRequestDTO);
Anime updateAnime(Long animeId, AnimeRequestDTO animeRequestDTO);

    void deleteAnime(Long animeId);

    Anime getAnimeById(Long animeId);

    List<Anime> getAllAnime();
      AnimeResponseDTO getAnimeDtoById(Long animeId);
    List<AnimeResponseDTO> getAllAnimeDto();

    List<AnimeResponseDTO> searchAnime(String query);


}
