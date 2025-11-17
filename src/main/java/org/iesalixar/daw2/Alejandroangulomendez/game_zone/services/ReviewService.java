package org.iesalixar.daw2.Alejandroangulomendez.game_zone.services;

import jakarta.validation.Valid;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.ReviewCreateDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.dtos.ReviewDTO;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.Review;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.User;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.entities.VideoGame;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.mappers.ReviewMapper;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.ReviewRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.UserRepository;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.repositories.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoGameRepository videoGameRepository;

    @Autowired
    private MessageSource messageSource;

    public Page<ReviewDTO> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(reviewMapper::toDTO);
    }

    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La reseña no existe"));
        return reviewMapper.toDTO(review);
    }

    public List<ReviewDTO> getReviewsByVideoGame(Long videoGameId) {
        List<Review> reviews = reviewRepository.findByVideoGameId(videoGameId);

        return reviews.stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

    public ReviewDTO createReview(ReviewCreateDTO dto, Locale locale, User user) {

        // 1️⃣ Verificar si ya existe review de ese usuario para ese videojuego
        if (reviewRepository.existsByUserIdAndVideoGameId(user.getId(), dto.getVideoGameId())) {
            throw new IllegalStateException("Ya has creado una review para este videojuego.");
        }

        // 2️⃣ Verificar que el videojuego existe
        VideoGame videoGame = videoGameRepository.findById(dto.getVideoGameId())
                .orElseThrow(() -> new IllegalArgumentException("El videojuego no existe"));

        Review review = reviewMapper.toEntity(dto);
        review.setUser(user);
        review.setVideoGame(videoGame);

        Review saved = reviewRepository.save(review);
        return reviewMapper.toDTO(saved);
    }

    public ReviewDTO updateReview(Long id, @Valid ReviewCreateDTO dto, Locale locale, User userAuthenticated) {

        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La reseña no existe"));

        // 🔒 SOLO el autor puede editar
        if (!existingReview.getUser().getId().equals(userAuthenticated.getId())) {
            throw new SecurityException("No puedes editar una review que no es tuya");
        }

        existingReview.setReviewText(dto.getReviewText());
        existingReview.setRating(dto.getRating());

        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.toDTO(updatedReview);
    }

    public void deleteReview(Long id, User currentUser) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La review no existe"));

        boolean isOwner = review.getUser().getId().equals(currentUser.getId());
        boolean isManager = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ROLE_MANAGER"));

        if (!isOwner && !isManager) {
            throw new IllegalArgumentException("No puedes borrar una review que no es tuya");
        }

        reviewRepository.delete(review);
    }


}