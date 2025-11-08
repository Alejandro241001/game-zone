package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.StudioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/studios")
public class StudioController {

    private static final Logger logger = LoggerFactory.getLogger(StudioController.class);

    @Autowired
    private StudioService studioService;

    /**
     * Obtiene todos los estudios almacenados en la base de datos.
     *
     * @return Lista de estudios
     */
    @Operation(summary = "Obtener todas los estudios", description = "Devuelve una lista de todos los estudios" +
            "disponibles en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estudios recuperada exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudioDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<StudioDTO>> getAllStudios(
            @PageableDefault(size = 10, sort = "name") Pageable pageable
    ) {
        logger.info("Solicitando todas las regiones con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<StudioDTO> studioDTOs = studioService.getAllStudios(pageable);
            return ResponseEntity.ok(studioDTOs);
        } catch (Exception e) {
            logger.error("Error al listar los estudios: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtiene todos los estudios almacenados en la base de datos.
     *
     * @param id Id de el estudio asociado
     */
    @Operation(summary = "Obtener un estudio", description = "Recupera un estudio" +
            "específico según su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudio encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estudio no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudioDTO> getStudioById(@PathVariable Long id) { // Cambiado de Long a Integer
        logger.info("Buscando estudio con ID {}", id);
        try {
            Optional<StudioDTO> studioDTO = studioService.getStudioById(id);
            return studioDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (Exception e) {
            logger.error("Error al buscar el estudio con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Crea un nuevo estudio
     * @param studioCreateDTO Datos para crear un estudio
     * @param locale Idioma para los mensajes de error
     * @return Estudio creado o mensaje de error
     */
    @Operation(summary = "Crear un nuevo estudio", description = "Permite registrar un nuevo estudio en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudio creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createStudio(@Valid @RequestBody StudioCreateDTO studioCreateDTO, Locale locale) {
        logger.info("Insertando nuevo estudio con nombre {}", studioCreateDTO.getName());
        try {
            StudioDTO createdStudio = studioService.createStudio(studioCreateDTO, locale);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudio);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear el estudio: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el estudio");
        }
    }

    /**
     * Actualiza un estudio existente
     * @param id ID del estudio a actualizar
     * @param studioCreateDTO Datos de actualización
     * @param locale Idioma para los mensajes de error
     * @return Estudio actualizado o mensaje de error
     */
    @Operation(summary = "Actualizar un estudio", description = "Permite actualizar los datos de un estudio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudio actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudio(@PathVariable Long id, @Valid @RequestBody StudioCreateDTO studioCreateDTO, Locale locale) { // Cambiado de Long a Integer
        try {
            StudioDTO updatedStudio = studioService.updateStudio(id, studioCreateDTO, locale);
            return ResponseEntity.ok(updatedStudio);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar el estudio con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el estudio");
        }
    }

    /**
     * Elimina un estudio por su ID
     *
     * @param id ID del estudio a eliminar
     * @return Resultado de la operación
     */
    @Operation(summary = "Eliminar un estudio", description = "Permite eliminar un estudio específico de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudio eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudio no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudio(@PathVariable Long id) { // Cambiado de Long a Integer
        try {
            studioService.deleteStudio(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar el estudio con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el estudio.");
        }
    }
}