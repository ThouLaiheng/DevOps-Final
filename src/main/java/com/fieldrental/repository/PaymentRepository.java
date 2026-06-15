package com.fieldrental.repository;

import com.fieldrental.model.Payment;
import com.fieldrental.model.Payment.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBookingId(Long bookingId);
    List<Payment> findByStatus(Status status);
}
