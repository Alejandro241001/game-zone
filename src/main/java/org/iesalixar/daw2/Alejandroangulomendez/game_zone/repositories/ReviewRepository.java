package org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories;


import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVideoGameId(Long videoGameId);

    boolean existsByUserIdAndVideoGameId(Long userId, Long videoGameId);

    Review findByUserIdAndVideoGameId(Long userId, Long videoGameId);
}
