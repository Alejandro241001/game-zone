package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.VideoGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * Controlador que maneja las operaciones CRUD para la entidad `VideoGame`.
 */
@RestController
@RequestMapping("/api/videogames")
public class VideoGameController {

    private static final Logger logger = LoggerFactory.getLogger(VideoGameController.class);

    @Autowired
    private VideoGameService videoGameService;

    // ===========================================
    // GET: Todos los videojuegos con plataformas
    // ===========================================
    @Operation(summary = "Obtener todos los videojuegos", description = "Devuelve una lista de todos los videojuegos con sus plataformas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de videojuegos recuperada existosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VideoGameDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<VideoGameDTO>> getAllVideogames() {
        logger.info("Solicitando todos los videojuegos con plataformas");
        try {
            // Usamos Pageable.unpaged() para obtener todos sin paginación
            List<VideoGameDTO> videogameDTOs = videoGameService.getAllVideoGames(PageRequest.of(0, Integer.MAX_VALUE))
                    .toList();
            return ResponseEntity.ok(videogameDTOs);
        } catch (Exception e) {
            logger.error("Error al listar los videojuegos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // ===========================================
    // GET: Videojuego por ID con plataformas
    // ===========================================
    @Operation(summary = "Obtener un videojuego por ID", description = "Recupera un videojuego con sus plataformas por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Videojuego encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoGameDTO.class))),
            @ApiResponse(responseCode = "404", description = "Videojuego no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VideoGameDTO> getVideoGameById(@PathVariable Long id) {
        logger.info("Buscando videojuego con ID {}", id);
        try {
            VideoGameDTO videoGameDTO = videoGameService.getVideoGameById(id)
                    .orElseThrow(() -> new IllegalArgumentException("VideoGame not found"));
            return ResponseEntity.ok(videoGameDTO);
        } catch (IllegalArgumentException e) {
            logger.warn("No se encontró el videojuego con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error al buscar el videojuego con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // ===========================================
    // POST: Crear videojuego
    // ===========================================
    @Operation(summary = "Crear un nuevo videojuego", description = "Permite registrar un nuevo videojuego en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Videojuego creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoGameDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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

    // ===========================================
    // PUT: Actualizar videojuego
    // ===========================================
    @Operation(summary = "Actualizar un videojuego", description = "Permite actualizar los datos de un videojuego existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Videojuego actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoGameDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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

    // ===========================================
    // DELETE: Eliminar videojuego
    // ===========================================
    @Operation(summary = "Eliminar un videojuego", description = "Permite eliminar un videojuego específico de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Videojuego eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Videojuego no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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