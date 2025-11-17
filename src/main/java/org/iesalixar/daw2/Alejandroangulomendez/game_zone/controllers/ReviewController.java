package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.ReviewCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.ReviewDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.UserRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Lista todas las reviews paginadas.
     */
    @Operation(summary = "Obtener todas las reviews", description = "Devuelve una lista de todas las reviews disponibles en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reviews recuperada exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReviewDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<ReviewDTO>> getAllReviews(Pageable pageable) {
        logger.info("Solicitando todas las reviews");
        try {
            Page<ReviewDTO> reviews = reviewService.getAllReviews(pageable);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            logger.error("Error al listar las reviews: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtiene una review específica por su ID.
     */
    @Operation(summary = "Obtener una review por ID", description = "Recupera una review específica según su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "404", description = "Review no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        logger.info("Buscando review con ID {}", id);
        try {
            ReviewDTO review = reviewService.getReviewById(id);
            return ResponseEntity.ok(review);
        } catch (IllegalArgumentException e) {
            logger.warn("Review no encontrada: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al buscar la review con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la review.");
        }
    }

    @GetMapping("/videogame/{videoGameId}")
    public ResponseEntity<?> getReviewsByVideoGame(@PathVariable Long videoGameId) {
        try {
            return ResponseEntity.ok(reviewService.getReviewsByVideoGame(videoGameId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener reviews del videojuego");
        }
    }

    /**
     * Inserta una nueva review.
     */
    @Operation(summary = "Crear una nueva review", description = "Permite registrar una nueva review en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewCreateDTO dto, Locale locale) {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        try {
            ReviewDTO saved = reviewService.createReview(dto, locale, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Actualiza una review existente.
     */
    @Operation(summary = "Actualizar una review", description = "Permite actualizar los datos de una review existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Review no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id,
                                          @Valid @RequestBody ReviewCreateDTO dto,
                                          Locale locale) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        try {
            return ResponseEntity.ok(reviewService.updateReview(id, dto, locale, user));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    /**
     * Elimina una review por su ID.
     */
    @Operation(summary = "Eliminar una review", description = "Permite eliminar una review específica de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Review no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        reviewService.deleteReview(id, user);

        return ResponseEntity.noContent().build();
    }
}