package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.ReviewDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Studio;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VideoGameMapper {

    public VideoGameDTO toDTO(VideoGame videoGame) {
        if (videoGame == null) return null;

        VideoGameDTO dto = new VideoGameDTO();
        dto.setId(videoGame.getId());
        dto.setName(videoGame.getName());
        dto.setDescription(videoGame.getDescription());
        dto.setMetacritic(videoGame.getMetacritic());
        dto.setReleaseYear(videoGame.getReleaseYear());

        // Studio
        if (videoGame.getStudio() != null) {
            StudioDTO studioDTO = new StudioDTO();
            studioDTO.setId(videoGame.getStudio().getId());
            studioDTO.setName(videoGame.getStudio().getName());
            dto.setStudio(studioDTO);
        }

        // Platforms
        if (videoGame.getPlatforms() != null && !videoGame.getPlatforms().isEmpty()) {
            List<PlatformDTO> platforms = videoGame.getPlatforms().stream()
                    .map(platform -> {
                        PlatformDTO pDto = new PlatformDTO();
                        pDto.setId(platform.getId());
                        pDto.setName(platform.getName());
                        return pDto;
                    })
                    .collect(Collectors.toList());
            dto.setPlatforms(platforms);
        } else {
            dto.setPlatforms(new ArrayList<>());
        }

        // Reviews
        if (videoGame.getReviews() != null && !videoGame.getReviews().isEmpty()) {
            List<ReviewDTO> reviews = videoGame.getReviews().stream()
                    .map(review -> {
                        ReviewDTO rDto = new ReviewDTO();
                        rDto.setId(review.getId());
                        rDto.setReviewText(review.getReviewText());
                        rDto.setRating(review.getRating());
                        rDto.setCreatedDate(review.getCreatedDate());
                        // Si tu ReviewDTO incluye info del usuario, puedes mapearlo aquí también
                        return rDto;
                    })
                    .collect(Collectors.toList());
            dto.setReviews(reviews);
        } else {
            dto.setReviews(new ArrayList<>());
        }

        return dto;
    }

    public VideoGame toEntity(VideoGameCreateDTO createDTO) {
        if (createDTO == null) return null;

        VideoGame videoGame = new VideoGame();
        videoGame.setName(createDTO.getName());
        videoGame.setDescription(createDTO.getDescription());
        videoGame.setMetacritic(createDTO.getMetacritic());
        videoGame.setReleaseYear(createDTO.getReleaseYear());

        Studio studio = new Studio();
        studio.setId(createDTO.getStudioId());
        videoGame.setStudio(studio);

        return videoGame;
    }
}