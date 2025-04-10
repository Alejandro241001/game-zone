package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Genre;

/**
 * Clase DTO (Data Transfer Object) que representa un género de videojuegos.
 */
@Getter
@Setter
public class GenreDTO {

    private Integer id;

    @Size(max = 100, message = "The genre name must not exceed 100 characters.")
    private String name;

    /**
     * Convierte una entidad Genre a un DTO.
     *
     * @param genre La entidad Genre a convertir.
     * @return Un nuevo GenreDTO con los datos de la entidad.
     */
    public static GenreDTO fromEntity(Genre genre) {
        if (genre == null) return null;
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }


}
