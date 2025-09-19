package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.GenreDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.GenreCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreDTO toDTO(Genre genre) {
        if (genre == null) return null;
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

    public Genre toEntity(GenreDTO dto) {
        if (dto == null) return null;
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        return genre;
    }

    public Genre toEntity(GenreCreateDTO createDTO) {
        if (createDTO == null) return null;
        Genre genre = new Genre();
        genre.setName(createDTO.getName());
        return genre;
    }
}