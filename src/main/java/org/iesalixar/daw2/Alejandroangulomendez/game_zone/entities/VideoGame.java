package org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "video_games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"studio", "genres", "platforms"})
@EqualsAndHashCode(exclude = {"studio", "genres", "platforms"})
public class VideoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The video game name cannot be empty.")
    @Size(max = 100, message = "The video game name must not exceed 100 characters.")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @ManyToMany
    @JoinTable(
            name = "video_games_genres",
            joinColumns = @JoinColumn(name = "video_game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @Lob
    @Column(name = "description")
    private String description;  // Descripción del videojuego

    @Column(name = "metacritic")
    private BigDecimal metacritic;

    @Column(name = "release_year")
    private Long releaseYear;

    // 👇 Relación con plataformas añadida
    @ManyToMany
    @JoinTable(
            name = "video_games_platforms",
            joinColumns = @JoinColumn(name = "video_game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id")
    )
    private Set<Platform> platforms = new HashSet<>();

    public VideoGame(String name, Studio studio) {
        this.name = name;
        this.studio = studio;
    }
}