package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;


import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.StudioRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Studio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/studios")
public class StudioController {

    private static final Logger logger = LoggerFactory.getLogger(StudioController.class);

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private MessageSource messageSource;

    // Lista de estudios
    @GetMapping
    public String listStudios(Model model) {
        logger.info("Solicitando la lista de todos los estudios...");
        List<Studio> listStudios = studioRepository.findAll();
        model.addAttribute("listStudios", listStudios);
        return "studio";
    }

    // Mostrar formulario para crear un nuevo estudio
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nuevo estudio.");
        model.addAttribute("studio", new Studio());
        return "studio-form";
    }

    // Mostrar formulario para editar un estudio existente
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        logger.info("Mostrando formulario de edición para el estudio con ID {}", id);
        Optional<Studio> studio = studioRepository.findById(id);
        model.addAttribute("studio", studio.get());
        return "studio-form";
    }

    // Insertar un nuevo estudio
    @PostMapping("/insert")
    public String insertStudio(@Valid @ModelAttribute("studio") Studio studio, BindingResult result, RedirectAttributes redirectAttributes, Locale locale, Model model) {
        logger.info("Insertando nuevo estudio con nombre {}", studio.getName());
        if (result.hasErrors()) {
            return "studio-form";
        }
        try {
            studioRepository.save(studio);
            logger.info("Estudio {} insertado con éxito.", studio.getName());
        } catch (Exception e) {
            logger.error("Error al insertar el estudio: {}", e.getMessage());
            String errorMessage = messageSource.getMessage("msg.studio-controller.insert.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        return "redirect:/studios";
    }

    // Actualizar un estudio existente
    @PostMapping("/update")
    public String updateStudio(@Valid @ModelAttribute("studio") Studio studio, BindingResult result, RedirectAttributes redirectAttributes, Locale locale, Model model) {
        logger.info("Actualizando estudio con ID {}", studio.getId());
        if (result.hasErrors()) {
            return "studio-form";
        }
        try {
            studioRepository.save(studio);
            logger.info("Estudio con ID {} actualizado con éxito.", studio.getId());
        } catch (Exception e) {
            logger.error("Error al actualizar el estudio con ID {}: {}", studio.getId(), e.getMessage());
            String errorMessage = messageSource.getMessage("msg.studio-controller.update.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        return "redirect:/studios";
    }

    // Eliminar un estudio
    @PostMapping("/delete")
    public String deleteStudio(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando estudio con ID {}", id);
        try {
            studioRepository.deleteById(id);
            logger.info("Estudio con ID {} eliminado con éxito.", id);
        } catch (Exception e) {
            logger.error("Error al eliminar el estudio con ID {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el estudio.");
        }
        return "redirect:/studios";
    }
}
