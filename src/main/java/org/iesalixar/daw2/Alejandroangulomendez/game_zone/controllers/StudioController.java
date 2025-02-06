package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.StudioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity<List<StudioDTO>> getAllStudios() {
        logger.info("Solicitando la lista de todos los estudios...");
        try {
            List<StudioDTO> studioDTOs = studioService.getAllStudios();
            return ResponseEntity.ok(studioDTOs);
        } catch (Exception e) {
            logger.error("Error al listar los estudios: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudioDTO> getStudioById(@PathVariable Long id) {
        logger.info("Buscando estudio con ID {}", id);
        try {
            Optional<StudioDTO> studioDTO = studioService.getStudioById(id);
            return studioDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (Exception e) {
            logger.error("Error al buscar el estudio con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudio(@PathVariable Long id, @Valid @RequestBody StudioCreateDTO studioCreateDTO, Locale locale) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudio(@PathVariable Long id) {
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

