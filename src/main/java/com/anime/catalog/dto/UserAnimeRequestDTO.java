package com.anime.catalog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAnimeRequestDTO {
    private Long animeId;          // The anime the user wants to add/update
    private String status;         // watchlist, favorite, completed, ongoing
}
