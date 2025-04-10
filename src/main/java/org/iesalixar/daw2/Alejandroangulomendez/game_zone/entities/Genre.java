package org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "genres") // Nombre de la tabla en la base de datos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"videoGames"}) // Excluir `videoGames` para evitar ciclos recursivos
@EqualsAndHashCode(exclude = {"videoGames"}) // Excluir `videoGames` de equals y hashCode
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The genre name cannot be empty.")
    @Size(max = 100, message = "The genre name must not exceed 100 characters.")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Relación uno a muchos: Un género puede tener muchos videojuegos
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VideoGame> videoGames;

    public Genre(String name) {
        this.name = name;
    }
}
