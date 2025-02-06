package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoGameDTO {

    private Long id;
    private String name;
    private StudioDTO studio;  // Relación con StudioDTO (similar a RegionDTO en el caso de ProvinceDTO)

}