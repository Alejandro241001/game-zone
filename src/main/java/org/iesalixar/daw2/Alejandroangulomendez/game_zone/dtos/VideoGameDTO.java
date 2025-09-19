package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class VideoGameDTO {

    private Long id;

    @Size(max = 100, message = "The video game name must not exceed 100 characters.")
    private String name;

    private StudioDTO studio;

    private String description;

    private BigDecimal metacritic;

    private Long releaseYear;

    private List<PlatformDTO> platforms;

    private List<ReviewDTO> reviews;
}