package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para la creación de un género de videojuego.
 */
@Getter
@Setter
public class GenreCreateDTO {

    @NotEmpty(message = "The genre name cannot be empty.")
    @Size(max = 100, message = "The genre name must not exceed 100 characters.")
    private String name;

}
