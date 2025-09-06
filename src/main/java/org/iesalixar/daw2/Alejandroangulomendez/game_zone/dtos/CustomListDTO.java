package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CustomListDTO {
    private Long id;
    private String name;
    private LocalDateTime createdDate;
    private Long userId; // 👈 solo guardamos el ID del usuario
    private List<VideoGameDTO> videoGames;
}