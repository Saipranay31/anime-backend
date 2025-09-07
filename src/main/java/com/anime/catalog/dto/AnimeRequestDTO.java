package com.anime.catalog.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnimeRequestDTO {
    private String name;
    private String synopsis;
    private int numberOfSeasons;
    private List<Long> genreIds; // list of genre IDs
    private String posterUrl; // poster for the anime

}
