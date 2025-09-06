package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.CustomListCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.CustomListDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.CustomListService;
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
@RequestMapping("/api/custom-lists")
public class CustomListController {

    private static final Logger logger = LoggerFactory.getLogger(CustomListController.class);

    @Autowired
    private CustomListService customListService;

    /**
     * Obtiene todas las listas personalizadas con paginación.
     *
     * @param pageable Paginación de la solicitud.
     * @return Página de listas.
     */
    @Operation(summary = "Obtener todas las listas personalizadas", description = "Devuelve una lista paginada de todas las listas creadas por usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listas recuperadas exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CustomListDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<CustomListDTO>> getAllCustomLists(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        try {
            Page<CustomListDTO> customLists = customListService.getAllCustomLists(pageable);
            return ResponseEntity.ok(customLists);
        } catch (Exception e) {
            logger.error("Error al obtener las listas personalizadas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtiene una lista personalizada por su ID.
     *
     * @param id Id de la lista.
     * @return Lista encontrada.
     */
    @Operation(summary = "Obtener una lista personalizada por ID", description = "Recupera una lista personalizada según su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomListDTO.class))),
            @ApiResponse(responseCode = "404", description = "Lista no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomListDTO> getCustomListById(@PathVariable Long id) {
        try {
            Optional<CustomListDTO> customList = customListService.getCustomListById(id);
            return customList.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (Exception e) {
            logger.error("Error al obtener la lista personalizada con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Crea una nueva lista personalizada.
     *
     * @param customListCreateDTO Datos para crear la lista.
     * @param locale Idioma para los mensajes de error.
     * @return Lista creada.
     */
    @Operation(summary = "Crear una nueva lista personalizada", description = "Permite que un usuario cree una nueva lista personalizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lista creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomListDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createCustomList(@Valid @RequestBody CustomListCreateDTO customListCreateDTO, Locale locale) {
        try {
            CustomListDTO createdList = customListService.createCustomList(customListCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear la lista personalizada: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la lista personalizada");
        }
    }

    /**
     * Actualiza una lista personalizada existente.
     *
     * @param id ID de la lista a actualizar.
     * @param customListCreateDTO Datos de actualización.
     * @param locale Idioma para los mensajes de error.
     * @return Lista actualizada.
     */
    @Operation(summary = "Actualizar una lista personalizada", description = "Permite actualizar los datos de una lista existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomListDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomList(@PathVariable Long id, @Valid @RequestBody CustomListCreateDTO customListCreateDTO, Locale locale) {
        try {
            CustomListDTO updatedList = customListService.updateCustomList(id, customListCreateDTO);
            return ResponseEntity.ok(updatedList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar la lista con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la lista personalizada");
        }
    }

    /**
     * Elimina una lista personalizada por su ID.
     *
     * @param id ID de la lista a eliminar.
     * @return Resultado de la operación.
     */
    @Operation(summary = "Eliminar una lista personalizada", description = "Permite eliminar una lista específica de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lista eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Lista no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomList(@PathVariable Long id) {
        try {
            customListService.deleteCustomList(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar la lista con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la lista personalizada");
        }
    }
}
