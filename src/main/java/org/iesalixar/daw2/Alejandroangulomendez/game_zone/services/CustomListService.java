package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;

import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.CustomListCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.CustomListDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.CustomList;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers.CustomListMapper;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.CustomListRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.UserRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomListService {

    @Autowired
    private CustomListRepository customListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoGameRepository videoGameRepository;

    // 1) Listado paginado
    public Page<CustomListDTO> getAllCustomLists(Pageable pageable) {
        return customListRepository.findAll(pageable)
                .map(CustomListMapper::toDTO);
    }

    // 2) Buscar por id
    public Optional<CustomListDTO> getCustomListById(Long id) {
        return customListRepository.findById(id)
                .map(CustomListMapper::toDTO);
    }

    // 3) Crear
    public CustomListDTO createCustomList(@Valid CustomListCreateDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        CustomList entity = new CustomList();
        entity.setName(dto.getName());
        entity.setUser(user);

        CustomList saved = customListRepository.save(entity);
        return CustomListMapper.toDTO(saved);
    }

    // 4) Actualizar
    public CustomListDTO updateCustomList(Long id, @Valid CustomListCreateDTO dto) {
        CustomList existing = customListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La lista no existe"));

        // Si envías otro userId, lo actualizamos (opcional)
        if (dto.getUserId() != null && (existing.getUser() == null
                || !dto.getUserId().equals(existing.getUser().getId()))) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
            existing.setUser(user);
        }

        existing.setName(dto.getName());

        CustomList updated = customListRepository.save(existing);
        return CustomListMapper.toDTO(updated);
    }

    // 5) Borrar
    public void deleteCustomList(Long id) {
        if (!customListRepository.existsById(id)) {
            throw new IllegalArgumentException("La lista no existe");
        }
        customListRepository.deleteById(id);
    }

    // 6) Añadir un videojuego a una lista personalizada
    public CustomListDTO addVideoGameToList(Long listId, Long videoGameId) {
        CustomList customList = customListRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("La lista no existe"));

        VideoGame videoGame = videoGameRepository.findById(videoGameId)
                .orElseThrow(() -> new IllegalArgumentException("El videojuego no existe"));

        customList.getVideoGames().add(videoGame);
        CustomList saved = customListRepository.save(customList);

        return CustomListMapper.toDTO(saved);
    }
}