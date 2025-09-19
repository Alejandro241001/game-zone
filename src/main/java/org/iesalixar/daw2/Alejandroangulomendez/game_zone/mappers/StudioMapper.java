package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Studio;
import org.springframework.stereotype.Component;

@Component
public class StudioMapper {

    public StudioDTO toDTO(Studio studio){
        if (studio == null) return null;
        StudioDTO dto = new StudioDTO();
        dto.setId(studio.getId());
        dto.setName(studio.getName());
        dto.setCountry(studio.getCountry());
        return dto;
    }

    public Studio toEntity(StudioDTO dto){
        if (dto == null) return null;
        Studio studio = new Studio();
        studio.setId(dto.getId());
        studio.setName(dto.getName());
        studio.setCountry(dto.getCountry());
        return studio;
    }

    public Studio toEntity(StudioCreateDTO createDTO){
        if (createDTO == null) return null;
        Studio studio = new Studio();
        studio.setName(createDTO.getName());
        studio.setCountry(createDTO.getCountry());
        return studio;
    }
}