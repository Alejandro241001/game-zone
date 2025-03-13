package org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Studio que extiende JpaRepository.
 * Proporciona operaciones CRUD y consultas personalizadas para la entidad Studio.
 */
public interface StudioRepository extends JpaRepository<Studio, Integer> {

    /**
     * Obtiene todos los estudios.
     *
     * @return una lista de todos los estudios.
     */
    List<Studio> findAll();

    /**
     * Inserta o actualiza un estudio.
     *
     * @param studio la entidad Studio a insertar o actualizar.
     * @return la entidad Studio insertada o actualizada.
     */
    Studio save(Studio studio);

    /**
     * Elimina un estudio por su ID.
     *
     * @param id el ID del estudio a eliminar.
     */
    void deleteById(Integer id);

    /**
     * Obtiene un estudio por su ID.
     *
     * @param id el ID del estudio.
     * @return un Optional que contiene el estudio si se encuentra, o vacío si no se encuentra.
     */
    Optional<Studio> findById(Integer id);

    /**
     * Comprueba si existe un estudio con un nombre específico.
     *
     * @param name el nombre del estudio.
     * @return true si existe un estudio con el nombre especificado, false en caso contrario.
     */
    boolean existsByName(String name);

    /**
     * Comprueba si existe un estudio con un nombre específico, excluyendo un estudio por su ID.
     *
     * @param name el nombre del estudio.
     * @param id el ID del estudio a excluir.
     * @return true si existe un estudio con el nombre especificado (excluyendo el estudio con el ID dado), false en caso contrario.
     */
    @Query("SELECT COUNT(s) > 0 FROM Studio s WHERE s.name = :name AND s.id != :id")
    boolean existsStudioByNameAndNotId(@Param("name") String name, @Param("id") Integer id);
}