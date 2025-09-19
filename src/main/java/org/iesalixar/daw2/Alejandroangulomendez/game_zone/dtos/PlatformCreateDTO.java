package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlatformCreateDTO {


    @NotBlank(message = "El nombre de la plataforma es obligatorio")
    @Size(max = 50, message = "El nombre de la plataforma no puede exceder 50 caracteres")
    private String name;
}