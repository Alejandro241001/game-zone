package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDTO {
    private String currentPassword;
    private String newPassword;
}
