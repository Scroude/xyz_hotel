package com.example.xyz_hotel.domain.wallet;

import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/wallet")
public class WalletController {
    @Autowired
    private WalletRepository walletRepository;

    @PutMapping(path ="/update")
    public @ResponseBody String updateWallet(@RequestBody Wallet wallet) {
        walletRepository.save(wallet);
        return  "Wallet updated";
    }

    @GetMapping(path = "/user")
    public @ResponseBody Wallet getUserWallet(@RequestBody User user) {
        return walletRepository.findWalletByUser(user);
    }
}
