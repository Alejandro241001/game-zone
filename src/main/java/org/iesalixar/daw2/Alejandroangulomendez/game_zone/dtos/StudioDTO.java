package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import lombok.Getter;
import lombok.Setter;

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
    private Long id;

    /**
     * Nombre del estudio de videojuegos.
     */
    private String name;

    /**
     * País donde se encuentra el estudio.
     */
    private String country;
}
