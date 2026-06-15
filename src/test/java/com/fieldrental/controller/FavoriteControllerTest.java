package com.fieldrental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fieldrental.model.Favorite;
import com.fieldrental.repository.FavoriteRepository;
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
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @BeforeEach
    void setUp() {
        favoriteRepository.deleteAll();
    }

    @Test
    void shouldAddFavorite() throws Exception {
        Favorite favorite = Favorite.builder()
                .userId(1L)
                .terrainId(1L)
                .build();

        mockMvc.perform(post("/api/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favorite)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnConflictForDuplicateFavorite() throws Exception {
        favoriteRepository.save(Favorite.builder().userId(1L).terrainId(1L).build());

        Favorite fav = Favorite.builder().userId(1L).terrainId(1L).build();
        mockMvc.perform(post("/api/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fav)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldGetFavoritesByUser() throws Exception {
        favoriteRepository.save(Favorite.builder().userId(1L).terrainId(1L).build());
        favoriteRepository.save(Favorite.builder().userId(1L).terrainId(2L).build());

        mockMvc.perform(get("/api/favorites/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldCheckFavorite() throws Exception {
        favoriteRepository.save(Favorite.builder().userId(1L).terrainId(1L).build());

        mockMvc.perform(get("/api/favorites/check")
                        .param("userId", "1")
                        .param("terrainId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void shouldRemoveFavorite() throws Exception {
        favoriteRepository.save(Favorite.builder().userId(1L).terrainId(1L).build());

        mockMvc.perform(delete("/api/favorites")
                        .param("userId", "1")
                        .param("terrainId", "1"))
                .andExpect(status().isNoContent());
    }
}
