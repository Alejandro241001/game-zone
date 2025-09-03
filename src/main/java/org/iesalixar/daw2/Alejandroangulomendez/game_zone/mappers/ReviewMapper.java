package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.ReviewCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.ReviewDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Review;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setReviewText(review.getReviewText());
        dto.setRating(review.getRating());
        dto.setCreatedDate(review.getCreatedDate());

        if (review.getUser() != null) {
            dto.setUserId(review.getUser().getId());
            dto.setUsername(review.getUser().getUsername());
        }

        if (review.getVideoGame() != null) {
            dto.setVideoGameId(review.getVideoGame().getId());
            dto.setVideoGameName(review.getVideoGame().getName());
        }

        return dto;
    }

    public Review toEntity(ReviewCreateDTO dto) {
        Review review = new Review();
        review.setReviewText(dto.getReviewText());
        review.setRating(dto.getRating());

        // Relacionaremos User y VideoGame en el servicio
        review.setUser(new User());
        review.setVideoGame(new VideoGame());

        return review;
    }
}
