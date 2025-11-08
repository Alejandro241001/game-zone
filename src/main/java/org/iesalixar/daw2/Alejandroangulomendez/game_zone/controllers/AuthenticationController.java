package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.AuthRequestDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.AuthResponseDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Role;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.RoleRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.UserRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==============================
    // 🔐 LOGIN
    // ==============================
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@Valid @RequestBody AuthRequestDTO authRequest) {
        try {
            if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(null, "El nombre de usuario y la contraseña son obligatorios"));
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            String username = authentication.getName();
            List<String> roles = authentication.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .toList();

            String token = jwtUtil.generateToken(username, roles);
            return ResponseEntity.ok(new AuthResponseDTO(token, "Autenticación exitosa"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO(null, "Credenciales inválidas. Por favor, verifica tus datos."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponseDTO(null, "Ocurrió un error inesperado: " + e.getMessage()));
        }
    }

    // ==============================
    // 🧾 REGISTRO
    // ==============================
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody User userRequest) {
        try {
            if (userRepository.existsByUsername(userRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(null, "El nombre de usuario ya está en uso"));
            }

            // Buscar o crear el rol USER
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

            // Crear nuevo usuario
            User newUser = new User();
            newUser.setUsername(userRequest.getUsername());
            newUser.setFirstName(userRequest.getFirstName() != null ? userRequest.getFirstName() : "Nombre");
            newUser.setLastName(userRequest.getLastName() != null ? userRequest.getLastName() : "Apellido");
            newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            newUser.setEnabled(true);
            newUser.setCreatedDate(LocalDateTime.now());
            newUser.setRoles(Collections.singleton(userRole));

            userRepository.save(newUser);

            // 🚀 Opcional: generar token automáticamente para login inmediato
            String token = jwtUtil.generateToken(newUser.getUsername(), List.of("ROLE_USER"));
            return ResponseEntity.ok(new AuthResponseDTO(token, "Usuario registrado exitosamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponseDTO(null, "Error al registrar usuario: " + e.getMessage()));
        }
    }

    // ==============================
    // ⚠️ MANEJO DE EXCEPCIONES
    // ==============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponseDTO> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponseDTO(null, "Ocurrió un error inesperado: " + e.getMessage()));
    }
}
