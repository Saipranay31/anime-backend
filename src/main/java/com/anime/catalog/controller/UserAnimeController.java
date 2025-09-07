package com.anime.catalog.controller;

import com.anime.catalog.dto.UserAnimeRequestDTO;
import com.anime.catalog.dto.UserAnimeResponseDTO;
import com.anime.catalog.service.UserAnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/api/user-anime")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserAnimeController {

    private final UserAnimeService userAnimeService;

    // helper to get current user's email (JWT subject)
    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Add or update (body: { animeId, status })
    @PostMapping
    public ResponseEntity<UserAnimeResponseDTO> addOrUpdate(@RequestBody UserAnimeRequestDTO request) {
        String email = getCurrentUserEmail();
        UserAnimeResponseDTO res = userAnimeService.addOrUpdateForUser(email, request);
        return ResponseEntity.ok(res);
    }

    // Remove (query param: ?animeId=123)
    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestParam Long animeId) {
        String email = getCurrentUserEmail();
        userAnimeService.removeForUser(email, animeId);
        return ResponseEntity.noContent().build();
    }

    // Get by status: /api/user-anime/status/WATCHLIST
    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserAnimeResponseDTO>> getByStatus(@PathVariable String status) {
        String email = getCurrentUserEmail();
        return ResponseEntity.ok(userAnimeService.getByStatusForUser(email, status));
    }

    // Get all entries for current user
    @GetMapping
    public ResponseEntity<List<UserAnimeResponseDTO>> getAllForUser() {
        String email = getCurrentUserEmail();
        return ResponseEntity.ok(userAnimeService.getAllForUser(email));
    }
}
