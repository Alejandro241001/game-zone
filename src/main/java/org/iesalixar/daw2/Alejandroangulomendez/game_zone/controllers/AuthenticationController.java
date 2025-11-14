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

    // ============================================================
    // 🔐 LOGIN
    // ============================================================
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@Valid @RequestBody AuthRequestDTO authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            List<String> roles = authentication.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .toList();

            String token = jwtUtil.generateToken(user.getUsername(), user.getId(), roles);

            return ResponseEntity.ok(new AuthResponseDTO(token, "Autenticación exitosa"));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO(null, "Credenciales inválidas"));
        }
    }


    // ============================================================
    // 🧾 REGISTRO
    // ============================================================
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody User userRequest) {
        try {
            if (userRepository.existsByUsername(userRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(null, "El nombre de usuario ya está en uso"));
            }

            // Asignar rol USER
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

            User newUser = new User();
            newUser.setUsername(userRequest.getUsername());
            newUser.setFirstName(userRequest.getFirstName());
            newUser.setLastName(userRequest.getLastName());
            newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            newUser.setEnabled(true);
            newUser.setCreatedDate(LocalDateTime.now());
            newUser.setRoles(Collections.singleton(userRole));

            userRepository.save(newUser);

            // Crear token con id + rol
            String token = jwtUtil.generateToken(
                    newUser.getUsername(),
                    newUser.getId(),
                    List.of("ROLE_USER")
            );

            return ResponseEntity.ok(new AuthResponseDTO(token, "Usuario registrado exitosamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponseDTO(null, "Error al registrar usuario"));
        }
    }

}
