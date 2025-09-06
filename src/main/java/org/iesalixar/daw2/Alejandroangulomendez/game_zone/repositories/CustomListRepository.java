package org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.CustomList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomListRepository extends JpaRepository<CustomList, Long> {

    // 🔎 Buscar todas las listas de un usuario
    List<CustomList> findByUserId(Long userId);
}