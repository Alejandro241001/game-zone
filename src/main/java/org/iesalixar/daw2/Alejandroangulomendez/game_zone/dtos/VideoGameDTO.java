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

    private List<ReviewDTO> reviews; // <-- añadido para reviews

    public static VideoGameDTO fromEntity(VideoGame videoGame) {
        if (videoGame == null) return null;

        VideoGameDTO dto = new VideoGameDTO();
        dto.setId(videoGame.getId());
        dto.setName(videoGame.getName());
        dto.setDescription(videoGame.getDescription());
        dto.setMetacritic(videoGame.getMetacritic());
        dto.setReleaseYear(videoGame.getReleaseYear());

        // Mapeo del estudio
        dto.setStudio(StudioDTO.fromEntity(videoGame.getStudio()));

        // Mapeo de plataformas
        if (videoGame.getPlatforms() != null) {
            List<PlatformDTO> platformDTOs = videoGame.getPlatforms()
                    .stream()
                    .map(PlatformDTO::fromEntity)
                    .collect(Collectors.toList());
            dto.setPlatforms(platformDTOs);
        }

        // Mapeo de reviews
        if (videoGame.getReviews() != null) {
            List<ReviewDTO> reviewDTOs = videoGame.getReviews()
                    .stream()
                    .map(ReviewDTO::fromEntity) // se asume que ReviewDTO tiene fromEntity
                    .collect(Collectors.toList());
            dto.setReviews(reviewDTOs);
        }

        return dto;
    }
}