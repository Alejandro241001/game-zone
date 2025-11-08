package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;

import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.StudioDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Studio;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers.StudioMapper;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.StudioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class StudioService {

    private static final Logger logger = LoggerFactory.getLogger(StudioService.class);

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private StudioMapper studioMapper;

    @Autowired
    private MessageSource messageSource;

    public Page<StudioDTO> getAllStudios(Pageable pageable) {
        logger.info("Solicitando todas los estudios con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Studio> studios = studioRepository.findAll(pageable);
            logger.info("Se han encontrado {} estudios en la página actual", studios.getNumberOfElements());
            return studios.map(studioMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de paginada de estudios: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<StudioDTO> getStudioById(Long id) { // Cambiado de Long a Integer
        try {
            logger.info("Buscando estudio con ID {}", id);
            return studioRepository.findById(id).map(studioMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar estudio con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el estudio", e);
        }
    }

    public StudioDTO createStudio(@Valid StudioCreateDTO studioCreateDTO, Locale locale) {
        if (studioRepository.existsByName(studioCreateDTO.getName())) {
            String errorMessage = messageSource.getMessage("msg.studio-controller.insert.nameExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        Studio studio = studioMapper.toEntity(studioCreateDTO);
        Studio savedStudio = studioRepository.save(studio);
        return studioMapper.toDTO(savedStudio);
    }

    public StudioDTO updateStudio(Long id, @Valid StudioCreateDTO studioCreateDTO, Locale locale) { // Cambiado de Long a Integer
        Studio existingStudio = studioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El estudio no existe"));

        if (studioRepository.existsStudioByNameAndNotId(studioCreateDTO.getName(), id)) {
            String errorMessage = messageSource.getMessage("msg.studio-controller.update.nameExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        existingStudio.setName(studioCreateDTO.getName());
        existingStudio.setCountry(studioCreateDTO.getCountry());
        Studio updatedStudio = studioRepository.save(existingStudio);
        return studioMapper.toDTO(updatedStudio);
    }

    public void deleteStudio(Long id) { // Cambiado de Long a Integer
        if (!studioRepository.existsById(id)) {
            throw new IllegalArgumentException("El estudio no existe");
        }
        studioRepository.deleteById(id);
    }
}