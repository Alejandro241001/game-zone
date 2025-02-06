package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.VideoGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Controlador que maneja las operaciones CRUD para la entidad `VideoGame`.
 */
@RestController
@RequestMapping("/api/videogames")
public class VideoGameController {

    private static final Logger logger = LoggerFactory.getLogger(VideoGameController.class);

    @Autowired
    private VideoGameService videoGameService;

    /**
     * Lista todos los videojuegos.
     *
     * @return ResponseEntity con la lista de videojuegos o un mensaje de error.
     */
    @GetMapping
    public ResponseEntity<List<VideoGameDTO>> getAllVideoGames() {
        logger.info("Solicitando la lista de todos los videojuegos...");
        try {
            List<VideoGameDTO> videoGameDTOs = videoGameService.getAllVideoGames();
            logger.info("Se han encontrado {} videojuegos.", videoGameDTOs.size());
            return ResponseEntity.ok(videoGameDTOs);
        } catch (Exception e) {
            logger.error("Error al listar los videojuegos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtiene un videojuego específico por su ID.
     *
     * @param id ID del videojuego.
     * @return ResponseEntity con el videojuego encontrado o un mensaje de error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VideoGameDTO> getVideoGameById(@PathVariable Long id) {
        logger.info("Buscando videojuego con ID {}", id);
        try {
            Optional<VideoGameDTO> videoGameDTO = videoGameService.getVideoGameById(id);
            if (videoGameDTO.isPresent()) {
                logger.info("Videojuego con ID {} encontrado.", id);
                return ResponseEntity.ok(videoGameDTO.get());
            } else {
                logger.warn("No se encontró ningún videojuego con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Error al buscar el videojuego con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Inserta un nuevo videojuego en la base de datos.
     *
     * @param videoGameCreateDTO DTO con los datos del nuevo videojuego.
     * @param locale             Idioma para los mensajes.
     * @return ResponseEntity con el videojuego creado o un mensaje de error.
     */
    @PostMapping
    public ResponseEntity<?> createVideoGame(@Valid @RequestBody VideoGameCreateDTO videoGameCreateDTO, Locale locale) {
        logger.info("Insertando nuevo videojuego con nombre {}", videoGameCreateDTO.getName());
        try {
            VideoGameDTO createdVideoGame = videoGameService.createVideoGame(videoGameCreateDTO, locale);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVideoGame);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear el videojuego: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear el videojuego: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el videojuego.");
        }
    }

    /**
     * Actualiza un videojuego existente.
     *
     * @param id                 ID del videojuego a actualizar.
     * @param videoGameCreateDTO DTO con los datos actualizados.
     * @param locale             Idioma para los mensajes.
     * @return ResponseEntity con el videojuego actualizado o un mensaje de error.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVideoGame(@PathVariable Long id, @Valid @RequestBody VideoGameCreateDTO videoGameCreateDTO, Locale locale) {
        logger.info("Actualizando videojuego con ID {}", id);
        try {
            VideoGameDTO updatedVideoGame = videoGameService.updateVideoGame(id, videoGameCreateDTO, locale);
            return ResponseEntity.ok(updatedVideoGame);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar el videojuego: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar el videojuego con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el videojuego.");
        }
    }

    /**
     * Elimina un videojuego por su ID.
     *
     * @param id ID del videojuego a eliminar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideoGame(@PathVariable Long id) {
        logger.info("Eliminando videojuego con ID {}", id);
        try {
            videoGameService.deleteVideoGame(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar el videojuego: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar el videojuego con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el videojuego.");
        }
    }
}
