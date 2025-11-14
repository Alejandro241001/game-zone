package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateDTO {

    @NotNull(message = "{msg.review.videogame.notNull}")
    private Long videoGameId;

    private String reviewText;

    @NotNull(message = "{msg.review.rating.notNull}")
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double rating;
}