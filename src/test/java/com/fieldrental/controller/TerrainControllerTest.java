package com.fieldrental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fieldrental.model.Terrain;
import com.fieldrental.repository.TerrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TerrainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TerrainRepository terrainRepository;

    @BeforeEach
    void setUp() {
        terrainRepository.deleteAll();
    }

    @Test
    void shouldCreateTerrain() throws Exception {
        Terrain terrain = Terrain.builder()
                .ownerId(1L)
                .title("Test Field")
                .location("Test Location")
                .areaSize(new BigDecimal("1000.00"))
                .pricePerDay(new BigDecimal("50.00"))
                .build();

        mockMvc.perform(post("/api/terrains")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(terrain)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Field"));
    }

    @Test
    void shouldGetAllTerrains() throws Exception {
        terrainRepository.save(Terrain.builder()
                .ownerId(1L)
                .title("Field 1")
                .location("Loc 1")
                .areaSize(new BigDecimal("500.00"))
                .pricePerDay(new BigDecimal("30.00"))
                .build());

        mockMvc.perform(get("/api/terrains"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldGetTerrainById() throws Exception {
        Terrain saved = terrainRepository.save(Terrain.builder()
                .ownerId(1L)
                .title("Field 1")
                .location("Loc 1")
                .areaSize(new BigDecimal("500.00"))
                .pricePerDay(new BigDecimal("30.00"))
                .build());

        mockMvc.perform(get("/api/terrains/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Field 1"));
    }

    @Test
    void shouldReturn404WhenTerrainNotFound() throws Exception {
        mockMvc.perform(get("/api/terrains/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteTerrain() throws Exception {
        Terrain saved = terrainRepository.save(Terrain.builder()
                .ownerId(1L)
                .title("To Delete")
                .location("Loc")
                .areaSize(new BigDecimal("500.00"))
                .pricePerDay(new BigDecimal("30.00"))
                .build());

        mockMvc.perform(delete("/api/terrains/{id}", saved.getId()))
                .andExpect(status().isNoContent());
    }
}
