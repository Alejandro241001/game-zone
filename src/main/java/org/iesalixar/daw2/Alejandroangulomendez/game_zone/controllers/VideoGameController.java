package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;


import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.VideoGameRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.StudioRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
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
@RequestMapping("/videogames")
public class VideoGameController {

    private static final Logger logger = LoggerFactory.getLogger(VideoGameController.class);

    @Autowired
    private VideoGameRepository videoGameRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private MessageSource messageSource;

    // Lista de videojuegos
    @GetMapping
    public String listVideoGames(Model model) {
        logger.info("Solicitando la lista de todos los videojuegos...");
        List<VideoGame> listVideoGames = videoGameRepository.findAll();
        model.addAttribute("listVideoGames", listVideoGames);
        return "videogame"; // Vista que mostrará la lista de videojuegos
    }

    // Mostrar formulario para crear un nuevo videojuego
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nuevo videojuego.");
        List<Studio> listStudios = studioRepository.findAll(); // Obtener los estudios disponibles
        model.addAttribute("videoGame", new VideoGame()); // Crear un nuevo objeto VideoGame vacío
        model.addAttribute("listStudios", listStudios); // Pasar los estudios a la vista
        return "videogame-form"; // Vista con el formulario para crear un nuevo videojuego
    }

    // Mostrar formulario para editar un videojuego existente
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        logger.info("Mostrando formulario de edición para el videojuego con ID {}", id);
        Optional<VideoGame> videoGame = videoGameRepository.findById(id); // Buscar el videojuego por ID
        List<Studio> listStudios = studioRepository.findAll(); // Obtener los estudios disponibles
        model.addAttribute("videoGame", videoGame.orElse(null)); // Pasar el videojuego a la vista
        model.addAttribute("listStudios", listStudios); // Pasar los estudios a la vista
        return "videogame-form"; // Vista con el formulario de edición
    }

    // Insertar un nuevo videojuego
    @PostMapping("/insert")
    public String insertVideoGame(@Valid @ModelAttribute("videoGame") VideoGame videoGame, BindingResult result, RedirectAttributes redirectAttributes, Locale locale, Model model) {
        logger.info("Insertando nuevo videojuego con nombre {}", videoGame.getName());
        if (result.hasErrors()) {
            List<Studio> listStudios = studioRepository.findAll(); // Obtener los estudios en caso de error
            model.addAttribute("listStudios", listStudios); // Pasar los estudios a la vista
            return "videogame-form"; // Volver al formulario con errores
        }
        try {
            videoGameRepository.save(videoGame); // Guardar el nuevo videojuego
            logger.info("Videojuego {} insertado con éxito.", videoGame.getName());
        } catch (Exception e) {
            logger.error("Error al insertar el videojuego: {}", e.getMessage());
            String errorMessage = messageSource.getMessage("msg.videogame-controller.insert.error", null, locale); // Mensaje de error
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage); // Redirigir con mensaje de error
        }
        return "redirect:/videogames"; // Redirigir a la lista de videojuegos
    }

    // Actualizar un videojuego existente
    @PostMapping("/update")
    public String updateVideoGame(@Valid @ModelAttribute("videoGame") VideoGame videoGame, BindingResult result, RedirectAttributes redirectAttributes, Locale locale, Model model) {
        logger.info("Actualizando videojuego con ID {}", videoGame.getId());
        if (result.hasErrors()) {
            List<Studio> listStudios = studioRepository.findAll(); // Obtener los estudios en caso de error
            model.addAttribute("listStudios", listStudios); // Pasar los estudios a la vista
            return "videogame-form"; // Volver al formulario con errores
        }
        try {
            videoGameRepository.save(videoGame); // Guardar los cambios del videojuego
            logger.info("Videojuego con ID {} actualizado con éxito.", videoGame.getId());
        } catch (Exception e) {
            logger.error("Error al actualizar el videojuego con ID {}: {}", videoGame.getId(), e.getMessage());
            String errorMessage = messageSource.getMessage("msg.videogame-controller.update.error", null, locale); // Mensaje de error
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage); // Redirigir con mensaje de error
        }
        return "redirect:/videogames"; // Redirigir a la lista de videojuegos
    }

    // Eliminar un videojuego
    @PostMapping("/delete")
    public String deleteVideoGame(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando videojuego con ID {}", id);
        try {
            videoGameRepository.deleteById(id); // Eliminar el videojuego por ID
            logger.info("Videojuego con ID {} eliminado con éxito.", id);
        } catch (Exception e) {
            logger.error("Error al eliminar el videojuego con ID {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el videojuego.");
        }
        return "redirect:/videogames"; // Redirigir a la lista de videojuegos
    }
}