package org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.CustomListDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.VideoGameDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.CustomList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomListMapper {

    @Autowired
    private VideoGameMapper videoGameMapper;

    public CustomListDTO toDTO(CustomList cl) {
        if (cl == null) return null;

        CustomListDTO dto = new CustomListDTO();
        dto.setId(cl.getId());
        dto.setName(cl.getName());
        dto.setCreatedDate(cl.getCreatedDate());

        if (cl.getUser() != null) {
            dto.setUserId(cl.getUser().getId()); // solo el ID del usuario
        }

        if (cl.getVideoGames() != null) {
            dto.setVideoGames(
                    cl.getVideoGames().stream()
                            .map(videoGameMapper::toDTO) // ✅ Usamos el mapper
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}