package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;


import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.GenreDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.GenreCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    /**
     * Convierte una entidad `Genre` a un `GenreDTO`.
     *
     * @param genre Entidad de Genre.
     * @return DTO correspondiente.
     */
    public GenreDTO toDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

    /**
     * Convierte un `GenreDTO` a una entidad `Genre`.
     *
     * @param dto DTO de Genre.
     * @return Entidad Genre.
     */
    public Genre toEntity(GenreDTO dto) {
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        return genre;
    }

    /**
     * Convierte un `GenreCreateDTO` a una entidad `Genre` (para creación).
     *
     * @param createDTO DTO para crear géneros.
     * @return Entidad Genre.
     */
    public Genre toEntity(GenreCreateDTO createDTO) {
        Genre genre = new Genre();
        genre.setName(createDTO.getName());
        return genre;
    }


}
