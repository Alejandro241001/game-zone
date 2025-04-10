package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;
import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers.VideoGameMapper;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.VideoGameRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.StudioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

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
    private MessageSource messageSource;

    public Page<VideoGameDTO> getAllVideoGames(Pageable pageable) {
        logger.info("Solicitando todos los videojuegos con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<VideoGame> videogames = videoGameRepository.findAll(pageable);
            logger.info("Se han encontrado {} videojuegos en la página actual", videogames.getNumberOfElements());
            return videogames.map(videoGameMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener la lista paginada de videojuegos: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<VideoGameDTO> getVideoGameById(Long id) {  // Cambié Integer por Long
        try {
            logger.info("Buscando videojuego con ID {}", id);
            return videoGameRepository.findById(id).map(videoGameMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar videojuego con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el videojuego", e);
        }
    }

    public VideoGameDTO createVideoGame(@Valid VideoGameCreateDTO videoGameCreateDTO, Locale locale) {
        if (videoGameRepository.existsByName(videoGameCreateDTO.getName())) {
            String errorMessage = messageSource.getMessage("msg.videogame-controller.insert.nameExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        if (!studioRepository.existsById(videoGameCreateDTO.getStudioId().intValue())) {
            String errorMessage = messageSource.getMessage("msg.videogame-controller.insert.studioNotExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        VideoGame videoGame = videoGameMapper.toEntity(videoGameCreateDTO);
        VideoGame savedVideoGame = videoGameRepository.save(videoGame);

        return videoGameMapper.toDTO(savedVideoGame);
    }

    public VideoGameDTO updateVideoGame(Long id, @Valid VideoGameCreateDTO videoGameCreateDTO, Locale locale) {
        VideoGame existingVideoGame = videoGameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El videojuego no existe"));

        if (videoGameRepository.existsVideoGameByNameAndNotId(videoGameCreateDTO.getName(), id)) {
            String errorMessage = messageSource.getMessage("msg.videogame-controller.update.nameExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        if (!studioRepository.existsById(videoGameCreateDTO.getStudioId().intValue())) {
            String errorMessage = messageSource.getMessage("msg.videogame-controller.update.studioNotExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        existingVideoGame.setName(videoGameCreateDTO.getName());
        existingVideoGame.setDescription(videoGameCreateDTO.getDescription()); // Establecer descripción
        existingVideoGame.setStudio(studioRepository.findById(videoGameCreateDTO.getStudioId().intValue()).orElseThrow(() ->
                new IllegalArgumentException("El estudio no existe"))
        );
        VideoGame updatedVideoGame = videoGameRepository.save(existingVideoGame);

        return videoGameMapper.toDTO(updatedVideoGame);
    }


    public void deleteVideoGame(Long id) {  // Cambié Integer por Long
        if (!videoGameRepository.existsById(id)) {
            throw new IllegalArgumentException("El videojuego no existe");
        }

        videoGameRepository.deleteById(id);
    }
}