package com.anime.catalog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "user_anime",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "anime_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // link to user
   @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;

    // link to anime
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    // WATCHLIST, FAVORITE, ONGOING, COMPLETED
    @Column(nullable = false)
    private String status;
}
