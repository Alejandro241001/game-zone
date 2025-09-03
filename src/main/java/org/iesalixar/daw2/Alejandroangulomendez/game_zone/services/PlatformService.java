package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;


import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PlatformDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Platform;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers.PlatformMapper;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlatformService {

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private PlatformMapper platformMapper;

    @Autowired
    private MessageSource messageSource;

    public List<PlatformDTO> findAll() {
        return platformRepository.findAll()
                .stream()
                .map(platformMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PlatformDTO> findById(Long id) {
        return platformRepository.findById(id)
                .map(platformMapper::toDTO);
    }

    public PlatformDTO create(@Valid PlatformCreateDTO dto, Locale locale) {
        if (platformRepository.existsByName(dto.getName())) {
            String errorMessage = messageSource.getMessage(
                    "msg.platform-controller.insert.nameExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        Platform platform = platformMapper.toEntity(dto);
        Platform savedPlatform = platformRepository.save(platform);
        return platformMapper.toDTO(savedPlatform);
    }

    public PlatformDTO update(Long id, @Valid PlatformCreateDTO dto, Locale locale) {
        Platform existingPlatform = platformRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La plataforma no existe"));

        if (platformRepository.existsPlatformByNameAndIdNot(dto.getName(), id)) {
            String errorMessage = messageSource.getMessage(
                    "msg.platform-controller.update.nameExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        existingPlatform.setName(dto.getName());
        Platform updatedPlatform = platformRepository.save(existingPlatform);
        return platformMapper.toDTO(updatedPlatform);
    }

    public void delete(Long id) {
        if (!platformRepository.existsById(id)) {
            throw new IllegalArgumentException("La plataforma no existe");
        }
        platformRepository.deleteById(id);
    }
}