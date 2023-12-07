package com.example.xyz_hotel.database;

import com.example.xyz_hotel.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findWalletByUserId(Long userId);
    void deleteWalletByUserId(Long userId);
}
