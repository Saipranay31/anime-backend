package com.anime.catalog.controller;

import com.anime.catalog.model.Anime;
import com.anime.catalog.model.Genre;
import com.anime.catalog.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.anime.catalog.dto.AnimeResponseDTO;
import com.anime.catalog.dto.GenreRequestDTO;
import com.anime.catalog.dto.GenreResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    // ✅ Create Genre
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GenreResponseDTO> createGenre(@RequestBody GenreRequestDTO dto) {
        Genre genre = new Genre();
        genre.setName(dto.getName());

        Genre saved = genreService.createGenre(genre);
        return ResponseEntity.ok(toDTO(saved));
    }

    // ✅ Update Genre
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> updateGenre(@PathVariable Long id, @RequestBody GenreRequestDTO dto) {
        Genre genre = new Genre();
        genre.setName(dto.getName());

        Genre updated = genreService.updateGenre(id, genre);
        return ResponseEntity.ok(toDTO(updated));
    }

    // ✅ Delete Genre
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok("Genre deleted successfully");
    }

    // ✅ Get All Genres
    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres() {
        List<GenreResponseDTO> response = genreService.getAllGenres()
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    // ✅ Get Genre by ID
    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> getGenreById(@PathVariable Long id) {
        Genre g = genreService.getGenreById(id);
        return ResponseEntity.ok(toDTO(g));
    }
   @GetMapping("/{id}/anime")
public ResponseEntity<List<AnimeResponseDTO>> getAnimeByGenre(@PathVariable Long id) {
    List<AnimeResponseDTO> animeList = genreService.getAnimeByGenre(id);
    return ResponseEntity.ok(animeList);
}

    // ✅ Helper method to map Genre -> DTO
    private GenreResponseDTO toDTO(Genre genre) {
        GenreResponseDTO dto = new GenreResponseDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }
}
