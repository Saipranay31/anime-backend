package com.anime.catalog.service;

import com.anime.catalog.dto.UserAnimeRequestDTO;
import com.anime.catalog.dto.UserAnimeResponseDTO;
import com.anime.catalog.model.UserAnime;
import com.anime.catalog.model.User;
import com.anime.catalog.model.Anime;
import com.anime.catalog.model.Genre;
import com.anime.catalog.repository.UserAnimeRepository;
import com.anime.catalog.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAnimeService {

    private final UserAnimeRepository userAnimeRepository;
    private final AnimeRepository animeRepository;
    private final UserService userService; // your existing service (has findByEmail)

    // Add or update a user-anime entry
    public UserAnimeResponseDTO addOrUpdateForUser(String userEmail, UserAnimeRequestDTO request) {
        User user = userService.findByEmail(userEmail);
        Anime anime = animeRepository.findById(request.getAnimeId())
                .orElseThrow(() -> new RuntimeException("Anime not found: " + request.getAnimeId()));

        UserAnime ua = userAnimeRepository.findByUserAndAnime(user, anime)
                .orElse(new UserAnime());

        ua.setUser(user);
        ua.setAnime(anime);
        ua.setStatus(request.getStatus() == null ? "WATCHLIST" : request.getStatus().toUpperCase());

        ua = userAnimeRepository.save(ua);

        return toDTO(ua);
    }

    // Remove entry
    public void removeForUser(String userEmail, Long animeId) {
        User user = userService.findByEmail(userEmail);
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("Anime not found: " + animeId));

        userAnimeRepository.findByUserAndAnime(user, anime).ifPresent(userAnimeRepository::delete);
    }

    // Get all entries for a user by status
    @Transactional(readOnly = true)
    public List<UserAnimeResponseDTO> getByStatusForUser(String userEmail, String status) {
        User user = userService.findByEmail(userEmail);
        return userAnimeRepository.findByUserAndStatus(user, status.toUpperCase())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Get all user entries
    @Transactional(readOnly = true)
    public List<UserAnimeResponseDTO> getAllForUser(String userEmail) {
        User user = userService.findByEmail(userEmail);
        return userAnimeRepository.findByUser(user)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Map entity -> response DTO
    private UserAnimeResponseDTO toDTO(UserAnime ua) {
        UserAnimeResponseDTO dto = new UserAnimeResponseDTO();
        Anime a = ua.getAnime();

        dto.setId(ua.getId());
        dto.setAnimeId(a.getId());
        dto.setName(a.getName());
        dto.setSynopsis(a.getSynopsis());
        dto.setPosterUrl(a.getPosterUrl());
        dto.setNumberOfSeasons(a.getNumberOfSeasons());

        // safe null check for genres
        if (a.getGenres() != null) {
            dto.setGenres(a.getGenres().stream().map(Genre::getName).collect(Collectors.toList()));
        }

        dto.setStatus(ua.getStatus());
        return dto;
    }


    public UserAnimeResponseDTO getAnimeDetailForUser(Long animeId, String userEmail) {
    Anime anime = animeRepository.findById(animeId)
            .orElseThrow(() -> new RuntimeException("Anime not found"));

    User user = userService.findByEmail(userEmail);
    Optional<UserAnime> uaOpt = userAnimeRepository.findByUserAndAnime(user, anime);

    UserAnimeResponseDTO dto = new UserAnimeResponseDTO();
    dto.setAnimeId(anime.getId());
    dto.setName(anime.getName());
    dto.setSynopsis(anime.getSynopsis());
    dto.setNumberOfSeasons(anime.getNumberOfSeasons());
    dto.setPosterUrl(anime.getPosterUrl());
    if (anime.getGenres() != null) {
        dto.setGenres(anime.getGenres().stream().map(Genre::getName).collect(Collectors.toList()));
    }
    uaOpt.ifPresent(ua -> dto.setStatus(ua.getStatus()));

    return dto;
}

}
