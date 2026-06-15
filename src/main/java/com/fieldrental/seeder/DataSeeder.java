package com.fieldrental.seeder;

import com.fieldrental.model.*;
import com.fieldrental.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TerrainRepository terrainRepository;
    private final TerrainImageRepository terrainImageRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;

    public DataSeeder(TerrainRepository terrainRepository,
                      TerrainImageRepository terrainImageRepository,
                      BookingRepository bookingRepository,
                      PaymentRepository paymentRepository,
                      ReviewRepository reviewRepository,
                      FavoriteRepository favoriteRepository) {
        this.terrainRepository = terrainRepository;
        this.terrainImageRepository = terrainImageRepository;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.reviewRepository = reviewRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void run(String... args) {
        if (terrainRepository.count() > 0) return;

        Terrain t1 = terrainRepository.save(Terrain.builder()
                .ownerId(1L)
                .title("Olympic Field")
                .description("Professional football field with grass")
                .location("Phnom Penh")
                .areaSize(new BigDecimal("5000.00"))
                .pricePerDay(new BigDecimal("150.00"))
                .availableFrom(LocalDateTime.now())
                .availableTo(LocalDateTime.now().plusMonths(6))
                .isAvailable(true)
                .build());

        Terrain t2 = terrainRepository.save(Terrain.builder()
                .ownerId(1L)
                .title("Sunset Stadium")
                .description("Indoor football field")
                .location("Siem Reap")
                .areaSize(new BigDecimal("4000.00"))
                .pricePerDay(new BigDecimal("120.00"))
                .availableFrom(LocalDateTime.now())
                .availableTo(LocalDateTime.now().plusMonths(3))
                .isAvailable(true)
                .build());

        Terrain t3 = terrainRepository.save(Terrain.builder()
                .ownerId(2L)
                .title("Victory Ground")
                .description("Community football field")
                .location("Battambang")
                .areaSize(new BigDecimal("6000.00"))
                .pricePerDay(new BigDecimal("100.00"))
                .isAvailable(true)
                .build());

        terrainImageRepository.save(TerrainImage.builder()
                .terrainId(t1.getId())
                .imagePath("/images/olympic1.jpg")
                .build());

        terrainImageRepository.save(TerrainImage.builder()
                .terrainId(t1.getId())
                .imagePath("/images/olympic2.jpg")
                .build());

        terrainImageRepository.save(TerrainImage.builder()
                .terrainId(t2.getId())
                .imagePath("/images/sunset1.jpg")
                .build());

        Booking b1 = bookingRepository.save(Booking.builder()
                .terrainId(t1.getId())
                .renterId(3L)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .totalPrice(new BigDecimal("450.00"))
                .status(Booking.Status.pending)
                .build());

        Booking b2 = bookingRepository.save(Booking.builder()
                .terrainId(t2.getId())
                .renterId(3L)
                .startDate(LocalDate.now().plusDays(5))
                .endDate(LocalDate.now().plusDays(6))
                .totalPrice(new BigDecimal("240.00"))
                .status(Booking.Status.approved)
                .build());

        paymentRepository.save(Payment.builder()
                .bookingId(b2.getId())
                .paymentMethod("ABA Pay")
                .amountPaid(new BigDecimal("240.00"))
                .status(Payment.Status.paid)
                .transactionId("TXN123456")
                .build());

        reviewRepository.save(Review.builder()
                .terrainId(t1.getId())
                .userId(3L)
                .rating(5)
                .comment("Excellent field!")
                .build());

        reviewRepository.save(Review.builder()
                .terrainId(t2.getId())
                .userId(3L)
                .rating(4)
                .comment("Good field, well maintained")
                .build());

        favoriteRepository.save(Favorite.builder()
                .userId(3L)
                .terrainId(t1.getId())
                .build());

        System.out.println("Database seeded successfully!");
    }
}
