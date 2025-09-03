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

    // Incluye plataformas y estudio en todas las páginas
    @EntityGraph(attributePaths = {"platforms", "studio"})
    Page<VideoGame> findAll(Pageable pageable);

    // ✅ Incluye plataformas, estudio y reviews cuando buscamos por ID
    @EntityGraph(attributePaths = {"platforms", "studio", "reviews"})
    Optional<VideoGame> findById(Long id);

    boolean existsVideoGameByNameAndIdNot(String name, Long id);
}