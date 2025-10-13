package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Role;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.RoleRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔹 Ver todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔹 Obtener un usuario por ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // 🔹 Crear un nuevo usuario
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
            user.setRoles(Set.of(defaultRole));
        }

        user.setEnabled(true);
        return userRepository.save(user);
    }

    // 🔹 Modificar un usuario existente
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);

        // Si se actualiza la contraseña, se cifra
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Campos básicos editables
        if (updatedUser.getUsername() != null) existingUser.setUsername(updatedUser.getUsername());
        if (updatedUser.getFirstName() != null) existingUser.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null) existingUser.setLastName(updatedUser.getLastName());
        if (updatedUser.getImage() != null) existingUser.setImage(updatedUser.getImage());

        // Estado del usuario
        existingUser.setEnabled(updatedUser.isEnabled());

        return userRepository.save(existingUser);
    }

    // 🔹 Cambiar los roles de un usuario
    public User updateUserRoles(Long id, List<String> roleNames) {
        User user = getUserById(id);
        Set<Role> newRoles = new HashSet<>();

        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName));
            newRoles.add(role);
        }

        user.setRoles(newRoles);
        return userRepository.save(user);
    }

    // 🔹 Activar / desactivar usuario
    public User toggleUserEnabled(Long id, boolean enabled) {
        User user = getUserById(id);
        user.setEnabled(enabled);
        return userRepository.save(user);
    }

    // 🔹 Eliminar usuario
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}