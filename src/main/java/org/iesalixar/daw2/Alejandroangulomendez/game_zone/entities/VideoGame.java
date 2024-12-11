package org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities;

import jakarta.persistence.*; // Anotaciones de JPA
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "video_games") // Nombre de la tabla en la base de datos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"studio"}) // Excluir `studio` para evitar ciclos recursivos.
@EqualsAndHashCode(exclude = {"studio"}) // Excluir `studio` de equals y hashCode.



public class VideoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The video game name cannot be empty.")
    @Size(max = 100, message = "The video game name must not exceed 100 characters.")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Relación con Studio: Muchos videojuegos pertenecen a un único estudio.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;


    public VideoGame(String name, Studio studio) {
        this.name = name;
        this.studio = studio;
    }

}
