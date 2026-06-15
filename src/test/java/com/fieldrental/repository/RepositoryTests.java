package com.fieldrental.repository;

import com.fieldrental.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RepositoryTests {

    @Autowired
    private TerrainRepository terrainRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void shouldSaveAndFindTerrain() {
        Terrain terrain = terrainRepository.save(Terrain.builder()
                .ownerId(1L)
                .title("Test Field")
                .location("Phnom Penh")
                .areaSize(new BigDecimal("1000.00"))
                .pricePerDay(new BigDecimal("50.00"))
                .build());

        assertNotNull(terrain.getId());
        assertTrue(terrain.getIsAvailable());

        Optional<Terrain> found = terrainRepository.findById(terrain.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Field", found.get().getTitle());
    }

    @Test
    void shouldFindAvailableTerrains() {
        terrainRepository.save(Terrain.builder()
                .ownerId(1L).title("F1").location("L1")
                .areaSize(new BigDecimal("500")).pricePerDay(new BigDecimal("30"))
                .isAvailable(true).build());
        terrainRepository.save(Terrain.builder()
                .ownerId(1L).title("F2").location("L2")
                .areaSize(new BigDecimal("500")).pricePerDay(new BigDecimal("30"))
                .isAvailable(false).build());

        List<Terrain> available = terrainRepository.findByIsAvailableTrue();
        assertEquals(1, available.size());
    }

    @Test
    void shouldSaveAndFindBooking() {
        Booking booking = bookingRepository.save(Booking.builder()
                .terrainId(1L)
                .renterId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .totalPrice(new BigDecimal("100.00"))
                .build());

        assertNotNull(booking.getId());
        assertEquals(Booking.Status.pending, booking.getStatus());
    }

    @Test
    void shouldFindBookingsByStatus() {
        bookingRepository.save(Booking.builder()
                .terrainId(1L).renterId(1L)
                .startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1))
                .totalPrice(new BigDecimal("50")).status(Booking.Status.approved)
                .build());

        List<Booking> approved = bookingRepository.findByTerrainIdAndStatus(1L, Booking.Status.approved);
        assertEquals(1, approved.size());
    }

    @Test
    void shouldSaveAndFindPayment() {
        Payment payment = paymentRepository.save(Payment.builder()
                .bookingId(1L)
                .paymentMethod("ABA Pay")
                .amountPaid(new BigDecimal("100.00"))
                .build());

        assertNotNull(payment.getId());
        assertEquals(Payment.Status.paid, payment.getStatus());
    }

    @Test
    void shouldSaveAndFindReview() {
        Review review = reviewRepository.save(Review.builder()
                .terrainId(1L)
                .userId(1L)
                .rating(5)
                .comment("Excellent")
                .build());

        assertNotNull(review.getId());
        assertEquals(5, review.getRating());
    }

    @Test
    void shouldSaveAndFindFavorite() {
        Favorite favorite = favoriteRepository.save(Favorite.builder()
                .userId(1L)
                .terrainId(1L)
                .build());

        assertNotNull(favorite.getId());
        assertTrue(favoriteRepository.existsByUserIdAndTerrainId(1L, 1L));
    }

    @Test
    void shouldEnforceUniqueFavorite() {
        favoriteRepository.save(Favorite.builder().userId(1L).terrainId(1L).build());

        assertThrows(Exception.class, () ->
                favoriteRepository.save(Favorite.builder().userId(1L).terrainId(1L).build())
        );
    }
}
