package org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"videoGames"})
@EqualsAndHashCode(exclude = {"videoGames"})
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The genre name cannot be empty.")
    @Size(max = 100, message = "The genre name must not exceed 100 characters.")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Relación muchos-a-muchos: Un género puede estar asociado con muchos videojuegos
    @ManyToMany(mappedBy = "genres", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VideoGame> videoGames = new ArrayList<>();

    public Genre(String name) {
        this.name = name;
    }
}
