package com.anime.catalog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAnimeResponseDTO {
    private Long id;               // ID of the UserAnime entry
    private Long animeId;          // ID of the anime
    private String name;           // Anime name
    private String synopsis;       // Anime synopsis
    private String posterUrl;      // Poster URL
    private int numberOfSeasons;   // Number of seasons
    private List<String> genres;   // List of genres
    private String status;         // User-specific status
}
