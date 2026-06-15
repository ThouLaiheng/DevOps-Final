package com.fieldrental.repository;

import com.fieldrental.model.Booking;
import com.fieldrental.model.Booking.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTerrainId(Long terrainId);
    List<Booking> findByRenterId(Long renterId);
    List<Booking> findByTerrainIdAndStatus(Long terrainId, Status status);
    List<Booking> findByRenterIdAndStatus(Long renterId, Status status);
}
