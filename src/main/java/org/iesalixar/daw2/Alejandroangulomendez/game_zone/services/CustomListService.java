package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;

import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.CustomListCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.CustomListDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.CustomList;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers.CustomListMapper;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.CustomListRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.VideoGameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomListService {

    @Autowired
    private CustomListRepository customListRepository;

    @Autowired
    private VideoGameRepository videoGameRepository;

    @Autowired
    private CustomListMapper customListMapper;

    // =========================
    // GET ALL
    // =========================
    public Page<CustomListDTO> getAllCustomLists(Pageable pageable) {
        return customListRepository.findAll(pageable)
                .map(customListMapper::toDTO);
    }

    public List<CustomListDTO> getListsByUser(User user) {
        return customListRepository.findByUser(user)
                .stream()
                .map(customListMapper::toDTO)
                .toList();
    }

    public Optional<CustomListDTO> getCustomListById(Long id) {
        return customListRepository.findById(id)
                .map(customListMapper::toDTO);
    }

    // =========================
    // CREATE (IGNORA dto.userId)
    // =========================
    public CustomListDTO createCustomList(CustomListCreateDTO dto, User authUser) {

        CustomList list = new CustomList();
        list.setName(dto.getName());
        list.setUser(authUser);

        return customListMapper.toDTO(customListRepository.save(list));
    }


    // =========================
    // UPDATE (solo dueño)
    // =========================
    public CustomListDTO updateCustomList(Long id, @Valid CustomListCreateDTO dto, User authUser) {

        CustomList list = customListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La lista no existe"));

        if (!list.getUser().getId().equals(authUser.getId())) {
            throw new SecurityException("No puedes editar una lista que no es tuya");
        }

        list.setName(dto.getName());

        CustomList updated = customListRepository.save(list);
        return customListMapper.toDTO(updated);
    }

    // =========================
    // DELETE (solo dueño)
    // =========================
    public void deleteCustomList(Long id, User authUser) {

        CustomList list = customListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La lista no existe"));

        if (!list.getUser().getId().equals(authUser.getId())) {
            throw new SecurityException("No puedes borrar esta lista");
        }

        customListRepository.delete(list);
    }

    // =========================
    // ADD GAME (solo dueño)
    // =========================
    public CustomListDTO addVideoGameToList(Long listId, Long videoGameId, User authUser) {

        CustomList list = customListRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("La lista no existe"));

        if (!list.getUser().getId().equals(authUser.getId())) {
            throw new SecurityException("No puedes modificar esta lista");
        }

        VideoGame game = videoGameRepository.findById(videoGameId)
                .orElseThrow(() -> new IllegalArgumentException("El videojuego no existe"));

        list.getVideoGames().add(game);

        CustomList saved = customListRepository.save(list);
        return customListMapper.toDTO(saved);
    }

    // =========================
    // REMOVE GAME (solo dueño)
    // =========================
    public CustomListDTO removeVideoGameFromList(Long listId, Long videoGameId, User authUser) {

        CustomList list = customListRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("La lista no existe"));

        if (!list.getUser().getId().equals(authUser.getId())) {
            throw new SecurityException("No puedes modificar esta lista");
        }

        VideoGame game = videoGameRepository.findById(videoGameId)
                .orElseThrow(() -> new IllegalArgumentException("El videojuego no existe"));

        list.getVideoGames().remove(game);

        CustomList saved = customListRepository.save(list);
        return customListMapper.toDTO(saved);
    }
}
