package org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {

    boolean existsByName(String name);

    // ✅ CORREGIDO: Incluye TODAS las relaciones LAZY que se mapean en el VideoGameDTO.
    // Esto previene la LazyInitializationException para el listado principal.
    @EntityGraph(attributePaths = {"studio", "genres", "platforms", "reviews"})
    Page<VideoGame> findAll(Pageable pageable);

    // ✅ Este método ya estaba bien para la vista de detalle
    @EntityGraph(attributePaths = {"platforms", "studio", "reviews", "genres"})
    Optional<VideoGame> findById(Long id);

    boolean existsVideoGameByNameAndIdNot(String name, Long id);
}