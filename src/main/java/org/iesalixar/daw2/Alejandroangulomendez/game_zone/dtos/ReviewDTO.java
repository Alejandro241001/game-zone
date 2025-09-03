package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;
import lombok.Getter;
import lombok.Setter;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Review;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDTO {

    private Long id;
    private Long userId;
    private Long videoGameId;
    private String reviewText;
    private Double rating;

    // ✅ Nuevos campos requeridos por el mapper
    private LocalDateTime createdDate;
    private String username;
    private String videoGameName;

    // Método para mapear de Review a ReviewDTO
    public static ReviewDTO fromEntity(Review review) {
        if (review == null) return null;

        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setUserId(review.getUser() != null ? review.getUser().getId() : null);
        dto.setVideoGameId(review.getVideoGame() != null ? review.getVideoGame().getId() : null);
        dto.setReviewText(review.getReviewText());
        dto.setRating(review.getRating());

        // ✅ Mapeo de los campos nuevos
        dto.setCreatedDate(review.getCreatedDate());
        dto.setUsername(review.getUser() != null ? review.getUser().getUsername() : null);
        dto.setVideoGameName(review.getVideoGame() != null ? review.getVideoGame().getName() : null);

        return dto;
    }
}