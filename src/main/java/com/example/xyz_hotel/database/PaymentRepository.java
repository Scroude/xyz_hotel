package com.example.xyz_hotel.database;

import com.example.xyz_hotel.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<List<Payment>> findPaymentsByWalletId(Long walletId);
}
