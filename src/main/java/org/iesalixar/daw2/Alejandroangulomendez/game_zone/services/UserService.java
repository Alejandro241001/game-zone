package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.PasswordChangeDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.UserDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.UserUpdateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers.UserMapper;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    // Obtener usuario actual
    public UserDTO getCurrentUser(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userMapper.toDTO(user);
    }

    // Actualizar datos personales
    public UserDTO updateUser(Principal principal, UserUpdateDTO dto) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    // Cambiar contraseña
    public void changePassword(Principal principal, PasswordChangeDTO dto) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("❌ Contraseña actual incorrecta");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    // Subir imagen de perfil
    public void updateImage(Principal principal, MultipartFile imageFile) throws IOException {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = "src/main/resources/static/img/";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = principal.getName() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, imageFile.getBytes());

            user.setImage(fileName);
            userRepository.save(user);
        }
    }
}
