package org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories;


import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad VideoGame que extiende JpaRepository.
 * Proporciona operaciones CRUD y consultas personalizadas para la entidad VideoGame.
 */
public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {

    /**
     * Comprueba si existe un videojuego con un nombre específico.
     *
     * @param name el nombre del videojuego.
     * @return true si existe un videojuego con el nombre especificado, false en caso contrario.
     */
    boolean existsByName(String name);

    /**
     * Comprueba si existe un videojuego con un nombre específico, excluyendo un videojuego por su ID.
     *
     * @param name el nombre del videojuego.
     * @param id el ID del videojuego a excluir.
     * @return true si existe un videojuego con el nombre especificado (excluyendo el videojuego con el ID dado), false en caso contrario.
     */
    @Query("SELECT COUNT(v) > 0 FROM VideoGame v WHERE v.name = :name AND v.id != :id")
    boolean existsVideoGameByNameAndNotId(@Param("name") String name, @Param("id") Long id);

    /**
     * Obtiene todos los videojuegos de un estudio específico.
     *
     * @param studioId el ID del estudio.
     * @return una lista de videojuegos que pertenecen a ese estudio.
     */
    List<VideoGame> findByStudioId(Long studioId);

    /**
     * Obtiene un videojuego por su ID.
     *
     * @param id el ID del videojuego.
     * @return un Optional con el videojuego si existe, o vacío si no se encuentra.
     */
    Optional<VideoGame>findById(Long id);
}