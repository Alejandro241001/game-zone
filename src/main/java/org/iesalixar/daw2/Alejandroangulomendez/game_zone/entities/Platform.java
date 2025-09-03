package org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "platforms")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToMany(mappedBy = "platforms")
    private Set<VideoGame> videoGames = new HashSet<>();

    // --- Constructores ---
    public Platform() {}

    public Platform(String name) {
        this.name = name;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<VideoGame> getVideoGames() {
        return videoGames;
    }

    public void setVideoGames(Set<VideoGame> videoGames) {
        this.videoGames = videoGames;
    }
}
