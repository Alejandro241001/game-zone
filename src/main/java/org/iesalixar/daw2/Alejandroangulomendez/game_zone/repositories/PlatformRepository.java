package org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
    boolean existsByName(String name);
}