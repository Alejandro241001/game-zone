package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class VideoGameCreateDTO {

    @NotEmpty(message = "{msg.videogame.name.notEmpty}")
    @Size(max = 100, message = "{msg.videogame.name.size}")
    private String name;

    @NotEmpty(message = "{msg.videogame.description.notEmpty}")
    @Size(max = 255, message = "{msg.videogame.description.size}")
    private String description;

    @NotNull(message = "{msg.videogame.studio.notNull}")
    private Long studioId;

    @NotNull(message = "{msg.videogame.metacritic.notNull}")
    private BigDecimal metacritic;

    @NotNull(message = "{msg.videogame.releaseYear.notNull}")
    private Long releaseYear;

    private String img;
    // NUEVO: IDs de plataformas asociadas
    private Set<Long> platformIds;
}
