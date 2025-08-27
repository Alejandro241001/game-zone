package org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Clase DTO (Data Transfer Object) que representa un videojuego.
 */
@Getter
@Setter
public class VideoGameDTO {

    private Long id;

    @Size(max = 100, message = "The video game name must not exceed 100 characters.")
    private String name;

    private StudioDTO studio; // Relación con StudioDTO

    private String description;

    private BigDecimal metacritic;    // Nuevo campo metacritic

    private Long releaseYear;   // Nuevo campo releaseYear

    private List<PlatformDTO> platforms; // <-- añadimos plataformas


    /**
     * Convierte una entidad VideoGame a un DTO.
     *
     * @param videoGame La entidad VideoGame a convertir.
     * @return Un nuevo VideoGameDTO con los datos de la entidad.
     */
    public static VideoGameDTO fromEntity(VideoGame videoGame) {
        if (videoGame == null) return null;

        VideoGameDTO dto = new VideoGameDTO();
        dto.setId(videoGame.getId());
        dto.setName(videoGame.getName());
        dto.setDescription(videoGame.getDescription());
        dto.setMetacritic(videoGame.getMetacritic());
        dto.setReleaseYear(videoGame.getReleaseYear());

        // Mapeo del estudio
        dto.setStudio(StudioDTO.fromEntity(videoGame.getStudio()));

        // Mapeo de plataformas
        if (videoGame.getPlatforms() != null) {
            List<PlatformDTO> platformDTOs = videoGame.getPlatforms()
                    .stream()
                    .map(PlatformDTO::fromEntity) // se asume que PlatformDTO tiene un método fromEntity
                    .collect(Collectors.toList());
            dto.setPlatforms(platformDTOs);
        }

        return dto;
    }
}