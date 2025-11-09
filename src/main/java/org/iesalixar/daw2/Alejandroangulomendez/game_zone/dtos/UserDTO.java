package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String image;

    // 👇 Añadir este campo opcional
    private boolean enabled;
}
