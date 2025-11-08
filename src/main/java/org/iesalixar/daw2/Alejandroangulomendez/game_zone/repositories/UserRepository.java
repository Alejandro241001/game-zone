package org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username el nombre de usuario a buscar.
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no existe.
     */
    Optional<User> findByUsername(String username);

    /**
     * Verifica si ya existe un usuario con un nombre de usuario dado.
     *
     * @param username el nombre de usuario a verificar.
     * @return true si existe, false si no.
     */
    boolean existsByUsername(String username); // 👈 añade esta línea
}
