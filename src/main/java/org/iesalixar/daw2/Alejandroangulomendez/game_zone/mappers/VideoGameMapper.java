package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformDTO;
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

    public VideoGameDTO toDTOWithPlatforms(VideoGame videoGame) {
        VideoGameDTO dto = new VideoGameDTO();
        dto.setId(videoGame.getId());
        dto.setName(videoGame.getName());
        dto.setDescription(videoGame.getDescription());

        // Studio
        StudioDTO studioDTO = new StudioDTO();
        if (videoGame.getStudio() != null) {
            studioDTO.setId(videoGame.getStudio().getId());
            studioDTO.setName(videoGame.getStudio().getName());
        }
        dto.setStudio(studioDTO);

        dto.setMetacritic(videoGame.getMetacritic());
        dto.setReleaseYear(videoGame.getReleaseYear());

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
            dto.setPlatforms(new ArrayList<>()); // Lista vacía si no hay plataformas
        }

        return dto;
    }

    public VideoGame toEntity(VideoGameCreateDTO createDTO) {
        VideoGame videoGame = new VideoGame();
        videoGame.setName(createDTO.getName());
        videoGame.setDescription(createDTO.getDescription());

        Studio studio = new Studio();
        studio.setId(createDTO.getStudioId());
        videoGame.setStudio(studio);

        videoGame.setMetacritic(createDTO.getMetacritic());
        videoGame.setReleaseYear(createDTO.getReleaseYear());

        return videoGame;
    }
}