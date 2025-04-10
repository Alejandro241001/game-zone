package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.GenreCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.GenreDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Genre;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers.GenreMapper;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreMapper genreMapper;

    public GenreDTO createGenre(GenreCreateDTO genreCreateDTO) {
        Genre genre = genreMapper.toEntity(genreCreateDTO);
        Genre savedGenre = genreRepository.save(genre);
        return genreMapper.toDTO(savedGenre);
    }

    public Optional<GenreDTO> getGenreById(Integer id) {
        return genreRepository.findById(id).map(genreMapper::toDTO);
    }

    public GenreDTO updateGenre(Integer id, GenreCreateDTO genreCreateDTO) {
        Genre existingGenre = genreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Genre not found"));

        existingGenre.setName(genreCreateDTO.getName());
        Genre updatedGenre = genreRepository.save(existingGenre);
        return genreMapper.toDTO(updatedGenre);
    }

    public void deleteGenre(Integer id) {
        genreRepository.deleteById(id);
    }

    public Page<GenreDTO> getAllGenres(Pageable pageable) {
        Page<Genre> genres = genreRepository.findAll(pageable);
        return genres.map(genreMapper::toDTO);
    }


}
