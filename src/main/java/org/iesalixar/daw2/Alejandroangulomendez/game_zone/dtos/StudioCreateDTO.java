package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para la creación de un estudio.
 *
 * Esta clase se utiliza para transferir datos al crear un nuevo estudio,
 * asegurando que se cumplan las validaciones necesarias.
 */
@Getter
@Setter
public class StudioCreateDTO {

    /**
     * Nombre del estudio.
     *
     * - No puede estar vacío (`@NotEmpty`).
     * - Longitud máxima de 100 caracteres (`@Size(max = 100)`).
     *
     * - Ejemplo: "Nintendo", "Ubisoft", "Rockstar Games".
     */
    @NotEmpty(message = "{msg.studio.name.notEmpty}")
    @Size(max = 100, message = "{msg.studio.name.size}")
    private String name;

    /**
     * País de origen del estudio.
     *
     * - No puede estar vacío (`@NotEmpty`).
     * - Longitud máxima de 50 caracteres (`@Size(max = 50)`).
     *
     * - Ejemplo: "Japón", "Francia", "Estados Unidos".
     */
    @NotEmpty(message = "{msg.studio.country.notEmpty}")
    @Size(max = 50, message = "{msg.studio.country.size}")
    private String country;
}