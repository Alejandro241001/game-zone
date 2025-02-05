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
import org.springframework.stereotype.Service;

import java.util.List;
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

    /**
     * Obtiene todos los videojuegos de la base de datos y los convierte a DTOs.
     *
     * @return Lista de objetos `VideoGameDTO` representando todos los videojuegos.
     */
    public List<VideoGameDTO> getAllVideoGames() {
        try {
            logger.info("Obteniendo todos los videojuegos...");
            List<VideoGame> videoGames = videoGameRepository.findAll();
            logger.info("Se encontraron {} videojuegos", videoGames.size());
            return videoGames.stream()
                    .map(videoGameMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los videojuegos: {}", e.getMessage());
            throw new RuntimeException("Error al obtener todos los videojuegos", e);
        }
    }

    /**
     * Busca un videojuego específico por su ID.
     *
     * @param id Identificador único del videojuego.
     * @return Un Optional que contiene un `VideoGameDTO` si el videojuego existe.
     */
    public Optional<VideoGameDTO> getVideoGameById(Long id) {
        try {
            logger.info("Buscando videojuego con ID {}", id);
            return videoGameRepository.findById(id).map(videoGameMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar videojuego con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el videojuego", e);
        }
    }

    /**
     * Crea un nuevo videojuego en la base de datos.
     *
     * @param videoGameCreateDTO DTO que contiene los datos del videojuego a crear.
     * @param locale             Idioma para los mensajes de error.
     * @return DTO del videojuego creado.
     */
    public VideoGameDTO createVideoGame(@Valid VideoGameCreateDTO videoGameCreateDTO, Locale locale) {
        // Verificar si el nombre del videojuego ya existe
        if (videoGameRepository.existsByName(videoGameCreateDTO.getName())) {
            String errorMessage = messageSource.getMessage("msg.videogame-controller.insert.nameExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        // Verificar si el estudio existe
        if (!studioRepository.existsById(videoGameCreateDTO.getStudioId())) {
            String errorMessage = messageSource.getMessage("msg.videogame-controller.insert.studioNotExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        // Convertir el DTO a entidad VideoGame (ya se maneja el estudio en el mapper)
        VideoGame videoGame = videoGameMapper.toEntity(videoGameCreateDTO);

        // Guardar el videojuego
        VideoGame savedVideoGame = videoGameRepository.save(videoGame);

        // Devolver el DTO del videojuego guardado
        return videoGameMapper.toDTO(savedVideoGame);
    }

    /**
     * Actualiza un videojuego existente.
     *
     * @param id                 Identificador del videojuego a actualizar.
     * @param videoGameCreateDTO DTO que contiene los nuevos datos del videojuego.
     * @param locale             Idioma para los mensajes de error.
     * @return DTO del videojuego actualizado.
     */
    public VideoGameDTO updateVideoGame(Long id, @Valid VideoGameCreateDTO videoGameCreateDTO, Locale locale) {
        VideoGame existingVideoGame = videoGameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El videojuego no existe"));

        if (videoGameRepository.existsVideoGameByNameAndNotId(videoGameCreateDTO.getName(), id)) {
            String errorMessage = messageSource.getMessage("msg.videogame-controller.update.nameExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        // Verificar si el estudio existe
        if (!studioRepository.existsById(videoGameCreateDTO.getStudioId())) {
            String errorMessage = messageSource.getMessage("msg.videogame-controller.update.studioNotExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        existingVideoGame.setName(videoGameCreateDTO.getName());
        existingVideoGame.setStudio(studioRepository.findById(videoGameCreateDTO.getStudioId()).orElseThrow(() ->
                new IllegalArgumentException("El estudio no existe"))
        );
        VideoGame updatedVideoGame = videoGameRepository.save(existingVideoGame);

        return videoGameMapper.toDTO(updatedVideoGame);
    }

    /**
     * Elimina un videojuego específico por su ID.
     *
     * @param id Identificador único del videojuego.
     * @throws IllegalArgumentException Si el videojuego no existe.
     */
    public void deleteVideoGame(Long id) {
        if (!videoGameRepository.existsById(id)) {
            throw new IllegalArgumentException("El videojuego no existe");
        }

        videoGameRepository.deleteById(id);
    }
}