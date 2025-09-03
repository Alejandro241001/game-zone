package org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "custom_lists")
public class CustomList {

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

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<VideoGame> getVideoGames() {
        return videoGames;
    }

    public void setVideoGames(Set<VideoGame> videoGames) {
        this.videoGames = videoGames;
    }

    // --- Métodos auxiliares para manejar la relación ---
    public void addVideoGame(VideoGame videoGame) {
        this.videoGames.add(videoGame);
    }

    public void removeVideoGame(VideoGame videoGame) {
        this.videoGames.remove(videoGame);
    }
}