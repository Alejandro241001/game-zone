package org.iesalixar.daw2.Alejandroangulomendez.game_zone.controllers;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')") // ✅ Solo ADMIN
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 🧾 Obtener todos los usuarios
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // 👤 Obtener un usuario por ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    // 🛠️ Actualizar roles
    @PutMapping("/users/{id}/roles")
    public ResponseEntity<User> updateUserRoles(@PathVariable Long id, @RequestBody List<String> roles) {
        return ResponseEntity.ok(adminService.updateUserRoles(id, roles));
    }

    // 🔒 Activar o desactivar usuario
    @PutMapping("/users/{id}/status")
    public ResponseEntity<User> toggleUserStatus(@PathVariable Long id, @RequestParam boolean enabled) {
        return ResponseEntity.ok(adminService.toggleUserEnabled(id, enabled));
    }

    // ❌ Eliminar usuario
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}