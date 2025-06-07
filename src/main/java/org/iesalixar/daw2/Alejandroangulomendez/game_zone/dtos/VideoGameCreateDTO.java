package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VideoGameCreateDTO {

    @NotEmpty(message = "{msg.videogame.name.notEmpty}")
    @Size(max = 100, message = "{msg.videogame.name.size}")
    private String name;


    @NotEmpty(message = "{msg.videogame.description.notEmpty}") // Validación de la descripción
    @Size(max = 255, message = "{msg.videogame.description.size}")
    private String description; // Agregar campo descripción

    /**
     * El ID del estudio al que pertenece el videojuego.
     *
     * - Debe ser un objeto válido.
     * - Este campo no puede ser nulo.
     */
    @NotNull(message = "{msg.videogame.studio.notNull}")
    private Long studioId;  // Referencia al ID del estudio al que pertenece el videojuego

    @NotNull(message = "{msg.videogame.metacritic.notNull}")
    private BigDecimal metacritic;

    @NotNull(message = "{msg.videogame.releaseYear.notNull}")
    private Long releaseYear;
}
