package org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "custom_lists")
public class CustomList {

    // --- Getters y Setters ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con usuario (muchas listas a un usuario)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    // Relación muchos-a-muchos con videojuegos
    @ManyToMany
    @JoinTable(
            name = "custom_lists_video_games",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "video_game_id")
    )
    private Set<VideoGame> videoGames = new HashSet<>();

    // --- Constructores ---
    public CustomList() {}

    public CustomList(User user, String name) {
        this.user = user;
        this.name = name;
    }

    // --- Métodos auxiliares para manejar la relación ---
    public void addVideoGame(VideoGame videoGame) {
        this.videoGames.add(videoGame);
    }

    public void removeVideoGame(VideoGame videoGame) {
        this.videoGames.remove(videoGame);
    }
}