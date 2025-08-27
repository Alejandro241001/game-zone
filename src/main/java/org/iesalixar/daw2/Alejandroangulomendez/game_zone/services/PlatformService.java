package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;


import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformCreateDTO;
import java.util.List;

public interface PlatformService {
    List<PlatformDTO> findAll();
    PlatformDTO findById(Long id);
    PlatformDTO create(PlatformCreateDTO dto);
    PlatformDTO update(Long id, PlatformCreateDTO dto);
    void delete(Long id);
}
