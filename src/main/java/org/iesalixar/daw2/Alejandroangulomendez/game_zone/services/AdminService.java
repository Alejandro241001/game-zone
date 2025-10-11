package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Role;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.RoleRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 🔹 Ver todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔹 Obtener un usuario por ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
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