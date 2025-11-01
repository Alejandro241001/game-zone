package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class VideoGameDTO {

    private Long id;

    // Se elimina @Size porque el DTO de lectura no necesita validar el tamaño del nombre, solo lo muestra.
    private String name;

    private StudioDTO studio;

    // CORRECCIÓN CLAVE: Tu entidad VideoGame tiene genres. El DTO debe incluirlo.
    private List<GenreDTO> genres;

    private String description;

    private BigDecimal metacritic;

    private Long releaseYear;

    private String img;

    private List<PlatformDTO> platforms;

    private List<ReviewDTO> reviews;
}