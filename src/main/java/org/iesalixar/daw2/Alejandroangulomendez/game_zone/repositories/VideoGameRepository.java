package org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories;


import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si los necesitas
}