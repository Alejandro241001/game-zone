package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;
import lombok.Getter;
import lombok.Setter;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Review;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private Long userId;
    private Long videoGameId;
    private String reviewText;
    private Double rating;
    private LocalDateTime createdDate;
    private String username;
    private String videoGameName;
}