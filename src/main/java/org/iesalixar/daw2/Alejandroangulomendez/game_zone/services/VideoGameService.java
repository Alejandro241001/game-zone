package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;

import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Genre;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Platform;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Studio;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers.VideoGameMapper;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.GenreRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.PlatformRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.StudioRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.VideoGameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VideoGameService {

    private static final Logger logger = LoggerFactory.getLogger(VideoGameService.class);

    @Autowired
    private VideoGameRepository videoGameRepository;

    @Autowired
    private VideoGameMapper videoGameMapper;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private GenreRepository genreRepository; // 👈 NUEVO

    @Autowired
    private MessageSource messageSource;

    public Page<VideoGameDTO> getAllVideoGames(Pageable pageable) {
        Page<VideoGame> videogames = videoGameRepository.findAll(pageable);
        return videogames.map(videoGameMapper::toDTO);
    }

    public Optional<VideoGameDTO> getVideoGameById(Long id) {
        return videoGameRepository.findById(id)
                .map(videoGameMapper::toDTO);
    }

    public VideoGameDTO createVideoGame(@Valid VideoGameCreateDTO dto, Locale locale) {
        if (videoGameRepository.existsByName(dto.getName())) {
            String errorMessage = messageSource.getMessage(
                    "msg.videogame-controller.insert.nameExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        Studio studio = studioRepository.findById(dto.getStudioId())
                .orElseThrow(() -> new IllegalArgumentException("El estudio no existe"));

        VideoGame videoGame = videoGameMapper.toEntity(dto);
        videoGame.setStudio(studio);

        // Mapear plataformas
        Set<Platform> platforms = new HashSet<>();
        if (dto.getPlatformIds() != null && !dto.getPlatformIds().isEmpty()) {
            dto.getPlatformIds().forEach(id -> {
                Platform platform = platformRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Plataforma con id " + id + " no existe"));
                platforms.add(platform);
            });
        }
        videoGame.setPlatforms(platforms);

        // 👇 NUEVO: Mapear géneros como List<Genre>
        List<Genre> genreList = new ArrayList<>();
        if (dto.getGenreIds() != null && !dto.getGenreIds().isEmpty()) {
            for (Long id : dto.getGenreIds()) {
                Genre genre = genreRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Género con id " + id + " no existe"));
                genreList.add(genre);
            }
        }
        videoGame.setGenres(genreList);

        VideoGame savedVideoGame = videoGameRepository.save(videoGame);
        return videoGameMapper.toDTO(savedVideoGame);
    }

    public VideoGameDTO updateVideoGame(Long id, @Valid VideoGameCreateDTO dto, Locale locale) {
        VideoGame existingVideoGame = videoGameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El videojuego no existe"));

        if (videoGameRepository.existsVideoGameByNameAndIdNot(dto.getName(), id)) {
            String errorMessage = messageSource.getMessage(
                    "msg.videogame-controller.update.nameExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        Studio studio = studioRepository.findById(dto.getStudioId())
                .orElseThrow(() -> new IllegalArgumentException("El estudio no existe"));

        existingVideoGame.setName(dto.getName());
        existingVideoGame.setDescription(dto.getDescription());
        existingVideoGame.setStudio(studio);
        existingVideoGame.setMetacritic(dto.getMetacritic());
        existingVideoGame.setReleaseYear(dto.getReleaseYear());

        // Mapear plataformas
        Set<Platform> platforms = new HashSet<>();
        if (dto.getPlatformIds() != null && !dto.getPlatformIds().isEmpty()) {
            dto.getPlatformIds().forEach(pid -> {
                Platform platform = platformRepository.findById(pid)
                        .orElseThrow(() -> new IllegalArgumentException("Plataforma con id " + pid + " no existe"));
                platforms.add(platform);
            });
        }
        existingVideoGame.setPlatforms(platforms);

        // 👇 NUEVO: Mapear géneros como List<Genre>
        List<Genre> genreList = new ArrayList<>();
        if (dto.getGenreIds() != null && !dto.getGenreIds().isEmpty()) {
            for (Long gid : dto.getGenreIds()) {
                Genre genre = genreRepository.findById(gid)
                        .orElseThrow(() -> new IllegalArgumentException("Género con id " + gid + " no existe"));
                genreList.add(genre);
            }
        }
        existingVideoGame.setGenres(genreList);

        VideoGame updatedVideoGame = videoGameRepository.save(existingVideoGame);
        return videoGameMapper.toDTO(updatedVideoGame);
    }

    public void deleteVideoGame(Long id) {
        if (!videoGameRepository.existsById(id)) {
            throw new IllegalArgumentException("El videojuego no existe");
        }
        videoGameRepository.deleteById(id);
    }
}
