package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.CustomListCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.CustomListDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.UserRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.CustomListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customlists")
public class CustomListController {

    @Autowired
    private CustomListService customListService;

    @Autowired
    private UserRepository userRepository;

    // CREATE
    @PostMapping
    public ResponseEntity<?> createCustomList(@Valid @RequestBody CustomListCreateDTO dto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(customListService.createCustomList(dto, user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getMyLists() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        return ResponseEntity.ok(customListService.getListsByUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getListById(@PathVariable Long id) {

        return customListService.getCustomListById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomList(
            @PathVariable Long id,
            @Valid @RequestBody CustomListCreateDTO dto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        try {
            return ResponseEntity.ok(customListService.updateCustomList(id, dto, user));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomList(@PathVariable Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        try {
            customListService.deleteCustomList(id, user);
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ADD GAME
    @PostMapping("/{listId}/videogames/{videoGameId}")
    public ResponseEntity<?> addGame(
            @PathVariable Long listId,
            @PathVariable Long videoGameId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        try {
            return ResponseEntity.ok(customListService.addVideoGameToList(listId, videoGameId, user));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // REMOVE GAME
    @DeleteMapping("/{listId}/videogames/{videoGameId}")
    public ResponseEntity<?> removeGame(
            @PathVariable Long listId,
            @PathVariable Long videoGameId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        try {
            return ResponseEntity.ok(customListService.removeVideoGameFromList(listId, videoGameId, user));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
