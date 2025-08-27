package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import lombok.Data;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Platform;

/**
 * DTO para la entidad Platform.
 */
@Data
public class PlatformDTO {
    private Long id;
    private String name;

    /**
     * Convierte una entidad Platform a DTO.
     *
     * @param platform La entidad Platform.
     * @return Un PlatformDTO con los datos de la entidad.
     */
    public static PlatformDTO fromEntity(Platform platform) {
        if (platform == null) return null;
        PlatformDTO dto = new PlatformDTO();
        dto.setId(platform.getId());
        dto.setName(platform.getName());
        return dto;
    }
}