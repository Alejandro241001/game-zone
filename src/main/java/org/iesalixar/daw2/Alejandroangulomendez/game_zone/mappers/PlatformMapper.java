package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Platform;
import org.springframework.stereotype.Component;

@Component
public class PlatformMapper {

    public PlatformDTO toDTO(Platform platform) {
        if (platform == null) return null;
        PlatformDTO dto = new PlatformDTO();
        dto.setId(platform.getId());
        dto.setName(platform.getName());
        return dto;
    }

    public Platform toEntity(PlatformDTO dto) {
        if (dto == null) return null;
        Platform platform = new Platform();
        platform.setId(dto.getId());
        platform.setName(dto.getName());
        return platform;
    }

    public Platform toEntity(PlatformCreateDTO createDTO) {
        if (createDTO == null) return null;
        Platform platform = new Platform();
        platform.setName(createDTO.getName());
        return platform;
    }
}