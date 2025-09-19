package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para la entidad Genre.
 */
@Getter
@Setter
public class GenreDTO {

    private Long id;

    @Size(max = 100, message = "The genre name must not exceed 100 characters.")
    private String name;

}
