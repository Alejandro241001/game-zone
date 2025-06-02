package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Studio;

/**
 * Clase DTO (Data Transfer Object) que representa un estudio de videojuegos.
 *
 * Esta clase se utiliza para transferir datos de un estudio entre las capas
 * de la aplicación, especialmente para exponerlos a través de la API sin incluir
 * información innecesaria o sensible.
 */
@Getter
@Setter
public class StudioDTO {

    /**
     * Identificador único del estudio.
     */
    private Long id; // Ajustado a Integer para coincidir con schema.sql

    /**
     * Nombre del estudio de videojuegos.
     */
    @Size(max = 100, message = "The studio name must not exceed 100 characters.")
    private String name;

    /**
     * País donde se encuentra el estudio.
     */
    @Size(max = 50, message = "The country name must not exceed 50 characters.")
    private String country;

    /**
     * Convierte una entidad Studio a un DTO.
     *
     * @param studio La entidad Studio a convertir.
     * @return Un nuevo StudioDTO con los datos de la entidad.
     */
    public static StudioDTO fromEntity(Studio studio) {
        if (studio == null) return null;
        StudioDTO dto = new StudioDTO();
        dto.setId(studio.getId());
        dto.setName(studio.getName());
        dto.setCountry(studio.getCountry());
        return dto;
    }
}