package com.example.xyz_hotel.database;

import com.example.xyz_hotel.domain.user.User;
import com.example.xyz_hotel.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findWalletByUser(User user);
    void deleteWalletByUser(User user);
}
