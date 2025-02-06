package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Studio;
import org.springframework.stereotype.Component;

@Component
public class StudioMapper {

    /**
     * Convierte una entidad `Studio` a un `StudioDTO` (datos básicos).
     *
     * @param studio Entidad de studio.
     * @return DTO correspondiente
     */
    public StudioDTO toDTO(Studio studio){
        StudioDTO dto = new StudioDTO();
        dto.setId(studio.getId());
        dto.setName(studio.getName());
        dto.setCountry(studio.getCountry());
        return dto;
    }

    /**
     * Convierte un `StudioDTO` a una entidad `Studio`.
     *
     * @param dto DTO de studio
     * @return Entidad Studio
     */
    public Studio toEntity(StudioDTO dto){
        Studio studio = new Studio();
        studio.setId(dto.getId());
        studio.setName(dto.getName());
        studio.setCountry(dto.getCountry());
        return studio;
    }

    /**
     * Convierte un `StudioCreateDTO` a una entidad `Studio` (para creación).
     *
     * @param createDTO DTO para crear estudios
     * @return Entidad Studio
     */
    public Studio toEntity(StudioCreateDTO createDTO){
        Studio studio = new Studio();
        studio.setName(createDTO.getName());
        studio.setCountry(createDTO.getCountry());
        return studio;
    }
}
