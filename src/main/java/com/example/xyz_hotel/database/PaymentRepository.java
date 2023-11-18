package com.example.xyz_hotel.database;

import com.example.xyz_hotel.domain.payment.Payment;
import com.example.xyz_hotel.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public List<Payment> findPaymentsByWallet(Wallet wallet);
}
