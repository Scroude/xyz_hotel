package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api/wallet")
public class WalletController {
    @Autowired
    private WalletRepository walletRepository;

    @PutMapping(path ="/update")
    public @ResponseBody String updateWallet(@RequestBody WalletRequest walletRequest) {
        Optional<Wallet> wallet = walletRepository.findById(walletRequest.getId());
        if (wallet.isPresent()) {
            wallet.get().setAmount(walletRequest.getAmount());
            wallet.get().setCurrency(walletRequest.getCurrency());
            walletRepository.save(wallet.get());
            return "Wallet updated";
        } else {
            return "Wallet Not Found";
        }
    }

    @GetMapping(path = "/{userId}")
    public @ResponseBody Wallet getUserWallet(@PathVariable Long userId) {
        return walletRepository.findWalletByUserId(userId);
    }
}
