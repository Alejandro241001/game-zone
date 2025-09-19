package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para estudios de videojuegos.
 */
@Getter
@Setter
public class StudioDTO {

    private Long id;

    @Size(max = 100, message = "The studio name must not exceed 100 characters.")
    private String name;

    @Size(max = 50, message = "The country name must not exceed 50 characters.")
    private String country;
}