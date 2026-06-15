package com.fieldrental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fieldrental.model.Booking;
import com.fieldrental.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
    }

    @Test
    void shouldCreateBooking() throws Exception {
        Booking booking = Booking.builder()
                .terrainId(1L)
                .renterId(1L)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(2))
                .totalPrice(new BigDecimal("100.00"))
                .build();

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("pending"));
    }

    @Test
    void shouldGetAllBookings() throws Exception {
        bookingRepository.save(Booking.builder()
                .terrainId(1L)
                .renterId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .totalPrice(new BigDecimal("50.00"))
                .build());

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldGetBookingById() throws Exception {
        Booking saved = bookingRepository.save(Booking.builder()
                .terrainId(1L)
                .renterId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .totalPrice(new BigDecimal("50.00"))
                .build());

        mockMvc.perform(get("/api/bookings/{id}", saved.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateBookingStatus() throws Exception {
        Booking saved = bookingRepository.save(Booking.builder()
                .terrainId(1L)
                .renterId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .totalPrice(new BigDecimal("50.00"))
                .build());

        mockMvc.perform(patch("/api/bookings/{id}/status", saved.getId())
                        .param("status", "approved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("approved"));
    }
}
