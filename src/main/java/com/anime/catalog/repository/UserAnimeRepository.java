package com.anime.catalog.repository;

import com.anime.catalog.model.UserAnime;
import com.anime.catalog.model.User;
import com.anime.catalog.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserAnimeRepository extends JpaRepository<UserAnime, Long> {
    Optional<UserAnime> findByUserAndAnime(User user, Anime anime);
    List<UserAnime> findByUserAndStatus(User user, String status);
    List<UserAnime> findByUser(User user);
    void deleteByUserAndAnime(User user, Anime anime);
    void deleteByUser(User user);
}
