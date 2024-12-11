package org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities;


import jakarta.persistence.*; // Anotaciones de JPA
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "studios") // Nombre de la tabla en la base de datos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"videoGames"}) // Excluir `videoGames` para evitar ciclos recursivos.
@EqualsAndHashCode(exclude = {"videoGames"}) // Excluir `videoGames` de equals y hashCode para evitar problemas.





public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The studio name cannot be empty.")
    @Size(max = 100, message = "The studio name must not exceed 100 characters.")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 50, message = "The country name must not exceed 50 characters.")
    @Column(name = "country", length = 50)
    private String country;

    // Relación uno a muchos: Un estudio puede tener muchos videojuegos.
    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VideoGame> videoGames;

    // Constructor adicional sin `id`
    public Studio(String name, String country) {
        this.name = name;
        this.country = country;
    }

}
