package com.anime.catalog.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@NoArgsConstructor
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 5000) // for long synopsis
    private String synopsis;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
@JoinTable(
    name = "anime_genre",
    joinColumns = @JoinColumn(name = "anime_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id")
)
@JsonManagedReference
private List<Genre> genres;
@Column(length = 1000) // in case URL is long
private String posterUrl;


    private int numberOfSeasons;


   
}
