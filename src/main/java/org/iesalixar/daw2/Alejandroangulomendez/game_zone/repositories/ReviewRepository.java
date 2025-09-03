package org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories;


import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
