package org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "video_games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"studio", "genres"})
@EqualsAndHashCode(exclude = {"studio", "genres"})
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
    private String description;  // Aquí agregamos la propiedad description

    // Nuevo campo metacritic
    @Column(name = "metacritic")
    private BigDecimal metacritic;

    // Nuevo campo releaseYear
    @Column(name = "release_year")
    private Long releaseYear;

    public VideoGame(String name, Studio studio) {
        this.name = name;
        this.studio = studio;
    }

}
