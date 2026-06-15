package com.fieldrental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fieldrental.model.Review;
import com.fieldrental.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
    }

    @Test
    void shouldCreateReview() throws Exception {
        Review review = Review.builder()
                .terrainId(1L)
                .userId(1L)
                .rating(5)
                .comment("Great!")
                .build();

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void shouldGetReviewsByTerrain() throws Exception {
        reviewRepository.save(Review.builder()
                .terrainId(1L)
                .userId(1L)
                .rating(4)
                .comment("Nice")
                .build());

        mockMvc.perform(get("/api/reviews/terrain/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldGetAverageRating() throws Exception {
        reviewRepository.save(Review.builder().terrainId(1L).userId(1L).rating(5).comment("A").build());
        reviewRepository.save(Review.builder().terrainId(1L).userId(2L).rating(3).comment("B").build());

        mockMvc.perform(get("/api/reviews/terrain/1/average-rating"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageRating").value(4.0));
    }
}
