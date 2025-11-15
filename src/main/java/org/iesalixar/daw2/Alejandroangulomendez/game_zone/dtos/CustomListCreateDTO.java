package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomListCreateDTO {



    @NotEmpty(message = "El nombre de la lista no puede estar vacío")
    private String name;
}
