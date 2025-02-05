package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoGameCreateDTO {

    @NotEmpty(message = "{msg.videogame.name.notEmpty}")
    @Size(max = 100, message = "{msg.videogame.name.size}")
    private String name;

    /**
     * El ID del estudio al que pertenece el videojuego.
     *
     * - Debe ser un objeto válido.
     * - Este campo no puede ser nulo.
     */
    @NotNull(message = "{msg.videogame.studio.notNull}")
    private Long studioId;  // Referencia al ID del estudio al que pertenece el videojuego
}
