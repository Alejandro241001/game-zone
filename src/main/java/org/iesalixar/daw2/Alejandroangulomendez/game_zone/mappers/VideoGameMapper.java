package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;


import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Studio;
import org.springframework.stereotype.Component;

@Component
public class VideoGameMapper {

    public VideoGameDTO toDTO(VideoGame videoGame) {
        VideoGameDTO dto = new VideoGameDTO();
        dto.setId(videoGame.getId());
        dto.setName(videoGame.getName());
        dto.setDescription(videoGame.getDescription());

        StudioDTO studioDTO = new StudioDTO();
        studioDTO.setId(videoGame.getStudio().getId());
        studioDTO.setName(videoGame.getStudio().getName());
        dto.setStudio(studioDTO);

        return dto;
    }

    public VideoGame toEntity(VideoGameCreateDTO createDTO) {
        VideoGame videoGame = new VideoGame();
        videoGame.setName(createDTO.getName());
        videoGame.setDescription(createDTO.getDescription());

        Studio studio = new Studio();
        studio.setId((long) createDTO.getStudioId().intValue());
        videoGame.setStudio(studio);

        return videoGame;
    }
}