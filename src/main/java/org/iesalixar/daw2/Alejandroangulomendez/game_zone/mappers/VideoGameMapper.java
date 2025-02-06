package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Studio;
import org.springframework.stereotype.Component;

@Component
public class VideoGameMapper {

    /**
     * Convierte una entidad VideoGame a un VideoGameDTO (datos básicos).
     *
     * @param videoGame Entidad de VideoGame.
     * @return DTO correspondiente.
     */
    public VideoGameDTO toDTO(VideoGame videoGame) {
        VideoGameDTO dto = new VideoGameDTO();
        dto.setId(videoGame.getId());
        dto.setName(videoGame.getName());

        // Mapear la relación con Studio a StudioDTO
        StudioDTO studioDTO = new StudioDTO();
        studioDTO.setId(videoGame.getStudio().getId());
        studioDTO.setName(videoGame.getStudio().getName()); // Asumiendo que Studio tiene nombre
        dto.setStudio(studioDTO);

        return dto;
    }

    /**
     * Convierte un VideoGameDTO a una entidad VideoGame.
     *
     * @param dto DTO de VideoGame.
     * @return Entidad VideoGame.
     */
    public VideoGame toEntity(VideoGameDTO dto) {
        VideoGame videoGame = new VideoGame();
        videoGame.setId(dto.getId());
        videoGame.setName(dto.getName());

        // Aquí se asume que StudioDTO contiene los datos necesarios para asociar un Studio
        Studio studio = new Studio();
        studio.setId(dto.getStudio().getId()); // Asumiendo que StudioDTO contiene el ID del estudio
        videoGame.setStudio(studio);

        return videoGame;
    }

    /**
     * Convierte un VideoGameCreateDTO a una entidad VideoGame (para creación).
     *
     * @param createDTO DTO para crear VideoGames.
     * @return Entidad VideoGame.
     */
    public VideoGame toEntity(VideoGameCreateDTO createDTO) {
        VideoGame videoGame = new VideoGame();
        videoGame.setName(createDTO.getName());

        // Aquí se supone que el DTO de creación contiene el ID del estudio (por ejemplo, studioId),
        // por lo que deberías asignar la relación correctamente.
        Studio studio = new Studio();
        studio.setId(createDTO.getStudioId()); // Asumí que el DTO contiene el ID del estudio
        videoGame.setStudio(studio);

        return videoGame;
    }
}