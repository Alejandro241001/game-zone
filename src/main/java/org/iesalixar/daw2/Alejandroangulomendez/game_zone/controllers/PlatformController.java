package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Platform;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers.PlatformMapper;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.PlatformRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/platforms")
public class PlatformController {

    private static final Logger logger = LoggerFactory.getLogger(PlatformController.class);

    private final PlatformRepository platformRepository;
    private final PlatformMapper platformMapper;

    public PlatformController(PlatformRepository platformRepository, PlatformMapper platformMapper) {
        this.platformRepository = platformRepository;
        this.platformMapper = platformMapper;
    }

    /**
     * Lista todas las plataformas.
     */
    @Operation(summary = "Obtener todas las plataformas", description = "Devuelve una lista de todas las plataformas disponibles en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de plataformas recuperada exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlatformDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<PlatformDTO>> getAllPlatforms() {
        logger.info("Solicitando todas las plataformas");
        try {
            List<PlatformDTO> platformDTOs = platformRepository.findAll()
                    .stream()
                    .map(platformMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(platformDTOs);
        } catch (Exception e) {
            logger.error("Error al listar las plataformas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtiene una plataforma específica por su ID.
     */
    @Operation(summary = "Obtener una plataforma por ID", description = "Recupera una plataforma específica según su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plataforma encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlatformDTO.class))),
            @ApiResponse(responseCode = "404", description = "Plataforma no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlatformDTO> getPlatformById(@PathVariable Long id) {
        logger.info("Buscando plataforma con ID {}", id);
        try {
            Optional<PlatformDTO> platformDTO = platformRepository.findById(id).map(platformMapper::toDTO);
            if (platformDTO.isPresent()) {
                logger.info("Plataforma con ID {} encontrada.", id);
                return ResponseEntity.ok(platformDTO.get());
            } else {
                logger.warn("No se encontró ninguna plataforma con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Error al buscar la plataforma con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Inserta una nueva plataforma.
     */
    @Operation(summary = "Crear una nueva plataforma", description = "Permite registrar una nueva plataforma en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plataforma creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlatformDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createPlatform(@Valid @RequestBody PlatformCreateDTO platformCreateDTO, Locale locale) {
        logger.info("Insertando nueva plataforma con nombre {}", platformCreateDTO.getName());
        try {
            if (platformRepository.existsByName(platformCreateDTO.getName())) {
                throw new IllegalArgumentException("Ya existe una plataforma con ese nombre");
            }
            Platform platform = platformMapper.toEntity(platformCreateDTO);
            Platform saved = platformRepository.save(platform);
            return ResponseEntity.status(HttpStatus.CREATED).body(platformMapper.toDTO(saved));
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear la plataforma: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear la plataforma: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la plataforma.");
        }
    }

    /**
     * Actualiza una plataforma existente.
     */
    @Operation(summary = "Actualizar una plataforma", description = "Permite actualizar los datos de una plataforma existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plataforma actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlatformDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Plataforma no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlatform(@PathVariable Long id, @Valid @RequestBody PlatformCreateDTO platformCreateDTO) {
        logger.info("Actualizando plataforma con ID {}", id);
        try {
            Optional<Platform> platformOptional = platformRepository.findById(id);
            if (platformOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plataforma no encontrada");
            }
            Platform platform = platformOptional.get();
            platform.setName(platformCreateDTO.getName());
            Platform updated = platformRepository.save(platform);
            return ResponseEntity.ok(platformMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar la plataforma: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar la plataforma con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la plataforma.");
        }
    }

    /**
     * Elimina una plataforma por su ID.
     */
    @Operation(summary = "Eliminar una plataforma", description = "Permite eliminar una plataforma específica de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plataforma eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Plataforma no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlatform(@PathVariable Long id) {
        logger.info("Eliminando plataforma con ID {}", id);
        try {
            if (!platformRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plataforma no encontrada");
            }
            platformRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar la plataforma con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la plataforma.");
        }
    }
}