package com.fieldrental.controller;

import com.fieldrental.model.Review;
import com.fieldrental.repository.ReviewRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return reviewRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/terrain/{terrainId}")
    public List<Review> getReviewsByTerrain(@PathVariable Long terrainId) {
        return reviewRepository.findByTerrainId(terrainId);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @GetMapping("/terrain/{terrainId}/average-rating")
    public ResponseEntity<Map<String, Object>> getAverageRating(@PathVariable Long terrainId) {
        Double avg = reviewRepository.findAverageRatingByTerrainId(terrainId);
        return ResponseEntity.ok(Map.of("terrainId", terrainId, "averageRating", avg != null ? avg : 0.0));
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
        Review saved = reviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @Valid @RequestBody Review review) {
        return reviewRepository.findById(id)
                .map(existing -> {
                    review.setId(id);
                    review.setCreatedAt(existing.getCreatedAt());
                    return ResponseEntity.ok(reviewRepository.save(review));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
