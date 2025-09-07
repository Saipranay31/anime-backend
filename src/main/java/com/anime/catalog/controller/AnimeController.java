package com.anime.catalog.controller;

import com.anime.catalog.dto.AnimeRequestDTO;
import com.anime.catalog.dto.AnimeResponseDTO;
import com.anime.catalog.dto.UserAnimeResponseDTO;
import com.anime.catalog.service.AnimeService;
import com.anime.catalog.service.UserAnimeService;
import com.anime.catalog.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;
    private final UserAnimeService userAnimeService;
    private final UserService userService;

    // ================= ADMIN ENDPOINTS =================

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AnimeResponseDTO> createAnime(@RequestBody AnimeRequestDTO dto) {
        return ResponseEntity.ok(animeService.getAnimeDtoById(animeService.createAnime(dto).getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AnimeResponseDTO> updateAnime(@PathVariable Long id, @RequestBody AnimeRequestDTO dto) {
        return ResponseEntity.ok(animeService.getAnimeDtoById(animeService.updateAnime(id, dto).getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnime(@PathVariable Long id) {
        animeService.deleteAnime(id);
        return ResponseEntity.ok("Anime deleted successfully");
    }

    // ================= PUBLIC / AUTHENTICATED ENDPOINTS =================

    // Get all anime (returns DTOs now)
    @GetMapping
    public ResponseEntity<List<AnimeResponseDTO>> getAllAnime() {
        return ResponseEntity.ok(animeService.getAllAnimeDto());
    }

    // Get anime by ID (returns DTO)
    @GetMapping("/{id}")
    public ResponseEntity<AnimeResponseDTO> getAnimeById(@PathVariable Long id) {
        return ResponseEntity.ok(animeService.getAnimeDtoById(id));
    }

    // Get user-specific anime details (status, progress, etc.)
    @GetMapping("/{id}/detail")
    public ResponseEntity<UserAnimeResponseDTO> getAnimeDetailForUser(@PathVariable Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAnimeResponseDTO dto = userAnimeService.getAnimeDetailForUser(id, userEmail);
        return ResponseEntity.ok(dto);
    }

    // Search anime by name / synopsis
    @GetMapping("/search")
    public ResponseEntity<List<AnimeResponseDTO>> searchAnime(@RequestParam String query) {
        return ResponseEntity.ok(animeService.searchAnime(query));
    }
}
