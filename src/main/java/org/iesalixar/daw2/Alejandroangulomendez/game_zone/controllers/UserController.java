package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PasswordChangeDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.UserDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 📄 Carpeta donde guardar imágenes
    private final String IMAGE_DIR = System.getProperty("user.dir") + "/img/";


    // ✅ Obtener datos del usuario autenticado
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = userOpt.get();
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setImage(user.getImage());

        return ResponseEntity.ok(dto);
    }

    // ✅ Actualizar nombre, apellidos, etc.
    @PutMapping("/me")
    public ResponseEntity<?> updateProfile(@RequestBody UserDTO userDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuario no encontrado"));
        }

        User user = userOpt.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        if (userDto.getUsername() != null && !userDto.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(userDto.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El nombre de usuario ya existe"));
            }
            user.setUsername(userDto.getUsername());
        }

        userRepository.save(user);

        // ✅ Devolvemos JSON para evitar errores de parseo
        UserDTO updatedUser = new UserDTO();
        updatedUser.setId(user.getId());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setImage(user.getImage());

        return ResponseEntity.ok(Map.of(
                "message", "Perfil actualizado correctamente",
                "user", updatedUser
        ));
    }


    // ✅ Cambiar contraseña
    @PutMapping("/me/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User user = userOpt.get();

        // ✅ Verificar la contraseña actual
        if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La contraseña actual no es correcta");
        }

        // ✅ Encriptar y guardar la nueva contraseña
        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok().body("{\"message\": \"Contraseña actualizada correctamente\"}");
    }

    // ✅ Subir foto de perfil
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User user = userOpt.get();

        try {
            // ✅ Carpeta absoluta del proyecto
            String uploadPath = System.getProperty("user.dir") + "/img/";
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            // ✅ Guardar archivo
            String fileName = username + "_" + file.getOriginalFilename();
            File destination = new File(dir, fileName);
            file.transferTo(destination);

            // ✅ Log de depuración
            logger.info("✅ Imagen guardada en: {}", destination.getAbsolutePath());

            // ✅ Actualizar usuario
            user.setImage(fileName);
            userRepository.save(user);

            return ResponseEntity.ok().body("{\"image\": \"" + fileName + "\"}");
        } catch (IOException e) {
            logger.error("❌ Error al guardar imagen: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la imagen: " + e.getMessage());
        }
    }

}
