package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.GenreCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.GenreDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private static final Logger logger = LoggerFactory.getLogger(GenreController.class);

    @Autowired
    private GenreService genreService;

    /**
     * Obtiene todos los géneros con paginación.
     *
     * @param pageable Paginación de la solicitud.
     * @return Página de géneros.
     */
    @Operation(summary = "Obtener todos los géneros", description = "Devuelve una lista paginada de todos los géneros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de géneros recuperada exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GenreDTO.class)))),

            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<GenreDTO>> getAllGenres(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        try {
            Page<GenreDTO> genreDTOs = genreService.getAllGenres(pageable);
            return ResponseEntity.ok(genreDTOs);
        } catch (Exception e) {
            logger.error("Error al obtener los géneros: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtiene un género por su ID.
     *
     * @param id Id del género.
     * @return Género encontrado.
     */
    @Operation(summary = "Obtener un género por ID", description = "Recupera un género específico según su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Género encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenreDTO.class))),
            @ApiResponse(responseCode = "404", description = "Género no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable Integer id) {
        try {
            Optional<GenreDTO> genreDTO = genreService.getGenreById(id);
            return genreDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (Exception e) {
            logger.error("Error al obtener el género con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Crea un nuevo género.
     *
     * @param genreCreateDTO Datos para crear un género.
     * @param locale Idioma para los mensajes de error.
     * @return Género creado.
     */
    @Operation(summary = "Crear un nuevo género", description = "Permite registrar un nuevo género en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Género creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenreDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createGenre(@Valid @RequestBody GenreCreateDTO genreCreateDTO, Locale locale) {
        try {
            GenreDTO createdGenre = genreService.createGenre(genreCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear el género: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el género");
        }
    }

    /**
     * Actualiza un género existente.
     *
     * @param id ID del género a actualizar.
     * @param genreCreateDTO Datos de actualización.
     * @param locale Idioma para los mensajes de error.
     * @return Género actualizado.
     */
    @Operation(summary = "Actualizar un género", description = "Permite actualizar los datos de un género existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Género actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenreDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenre(@PathVariable Integer id, @Valid @RequestBody GenreCreateDTO genreCreateDTO, Locale locale) {
        try {
            GenreDTO updatedGenre = genreService.updateGenre(id, genreCreateDTO);
            return ResponseEntity.ok(updatedGenre);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar el género con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el género");
        }
    }

    /**
     * Elimina un género por su ID.
     *
     * @param id ID del género a eliminar.
     * @return Resultado de la operación.
     */
    @Operation(summary = "Eliminar un género", description = "Permite eliminar un género específico de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Género eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Género no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Integer id) {
        try {
            genreService.deleteGenre(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar el género con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el género.");
        }
    }
//
}
