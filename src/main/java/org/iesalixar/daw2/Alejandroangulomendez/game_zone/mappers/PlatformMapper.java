package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Platform;
import org.springframework.stereotype.Component;

@Component
public class PlatformMapper {

    /**
     * Convierte una entidad `Platform` a un `PlatformDTO`.
     *
     * @param platform Entidad de Platform.
     * @return DTO correspondiente.
     */
    public PlatformDTO toDTO(Platform platform) {
        PlatformDTO dto = new PlatformDTO();
        dto.setId(platform.getId());
        dto.setName(platform.getName());
        return dto;
    }

    /**
     * Convierte un `PlatformDTO` a una entidad `Platform`.
     *
     * @param dto DTO de Platform.
     * @return Entidad Platform.
     */
    public Platform toEntity(PlatformDTO dto) {
        Platform platform = new Platform();
        platform.setId(dto.getId());
        platform.setName(dto.getName());
        return platform;
    }

    /**
     * Convierte un `PlatformCreateDTO` a una entidad `Platform` (para creación).
     *
     * @param createDTO DTO para crear plataformas.
     * @return Entidad Platform.
     */
    public Platform toEntity(PlatformCreateDTO createDTO) {
        Platform platform = new Platform();
        platform.setName(createDTO.getName());
        return platform;
    }
}