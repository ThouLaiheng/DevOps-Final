package com.fieldrental.controller;

import com.fieldrental.model.Booking;
import com.fieldrental.model.Booking.Status;
import com.fieldrental.repository.BookingRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/terrain/{terrainId}")
    public List<Booking> getBookingsByTerrain(@PathVariable Long terrainId) {
        return bookingRepository.findByTerrainId(terrainId);
    }

    @GetMapping("/renter/{renterId}")
    public List<Booking> getBookingsByRenter(@PathVariable Long renterId) {
        return bookingRepository.findByRenterId(renterId);
    }

    @GetMapping("/terrain/{terrainId}/status/{status}")
    public List<Booking> getBookingsByTerrainAndStatus(@PathVariable Long terrainId, @PathVariable Status status) {
        return bookingRepository.findByTerrainIdAndStatus(terrainId, status);
    }

    @GetMapping("/renter/{renterId}/status/{status}")
    public List<Booking> getBookingsByRenterAndStatus(@PathVariable Long renterId, @PathVariable Status status) {
        return bookingRepository.findByRenterIdAndStatus(renterId, status);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @Valid @RequestBody Booking booking) {
        return bookingRepository.findById(id)
                .map(existing -> {
                    booking.setId(id);
                    booking.setCreatedAt(existing.getCreatedAt());
                    return ResponseEntity.ok(bookingRepository.save(booking));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id, @RequestParam Status status) {
        return bookingRepository.findById(id)
                .map(existing -> {
                    existing.setStatus(status);
                    return ResponseEntity.ok(bookingRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
