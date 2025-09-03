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
import org.springframework.stereotype.Service;

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

    public ReviewDTO createReview(@Valid ReviewCreateDTO dto, Locale locale) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        VideoGame videoGame = videoGameRepository.findById(dto.getVideoGameId())
                .orElseThrow(() -> new IllegalArgumentException("El videojuego no existe"));

        Review review = reviewMapper.toEntity(dto);
        review.setUser(user);
        review.setVideoGame(videoGame);

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(savedReview);
    }

    public ReviewDTO updateReview(Long id, @Valid ReviewCreateDTO dto, Locale locale) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La reseña no existe"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        VideoGame videoGame = videoGameRepository.findById(dto.getVideoGameId())
                .orElseThrow(() -> new IllegalArgumentException("El videojuego no existe"));

        existingReview.setReviewText(dto.getReviewText());
        existingReview.setRating(dto.getRating());
        existingReview.setUser(user);
        existingReview.setVideoGame(videoGame);

        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.toDTO(updatedReview);
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("La reseña no existe");
        }
        reviewRepository.deleteById(id);
    }
}