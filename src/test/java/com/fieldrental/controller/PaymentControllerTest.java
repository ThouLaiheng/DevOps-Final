package com.fieldrental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fieldrental.model.Payment;
import com.fieldrental.repository.PaymentRepository;
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
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
    }

    @Test
    void shouldCreatePayment() throws Exception {
        Payment payment = Payment.builder()
                .bookingId(1L)
                .paymentMethod("ABA Pay")
                .amountPaid(new BigDecimal("100.00"))
                .build();

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("paid"));
    }

    @Test
    void shouldGetAllPayments() throws Exception {
        paymentRepository.save(Payment.builder()
                .bookingId(1L)
                .paymentMethod("Cash")
                .amountPaid(new BigDecimal("50.00"))
                .build());

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldGetPaymentById() throws Exception {
        Payment saved = paymentRepository.save(Payment.builder()
                .bookingId(1L)
                .paymentMethod("Cash")
                .amountPaid(new BigDecimal("50.00"))
                .build());

        mockMvc.perform(get("/api/payments/{id}", saved.getId()))
                .andExpect(status().isOk());
    }
}
