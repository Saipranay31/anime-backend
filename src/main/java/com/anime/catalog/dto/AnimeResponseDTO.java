package com.anime.catalog.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnimeResponseDTO {
    private Long id;
    private String name;
    private String synopsis;
    private int numberOfSeasons;
    private List<String> genres; // genre names
    private String posterUrl; // return the poster URL to frontend

}
