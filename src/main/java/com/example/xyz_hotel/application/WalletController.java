package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.Wallet;
import com.example.xyz_hotel.exeption.not_found.WalletNotFoundException;
import com.example.xyz_hotel.exeption.nulls.NullUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/wallet")
public class WalletController {
    @Autowired
    private WalletRepository walletRepository;

    //Récupération du wallet
    @GetMapping(path = "/{userId}")
    public @ResponseBody ResponseEntity<Wallet> getUserWallet(@PathVariable Long userId) {
        //Check the data
        if (userId == null) throw new NullUserException();

        //Get the wallet
        Wallet wallet = walletRepository.findWalletByUserId(userId)
                .orElseThrow(WalletNotFoundException::new);
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }
}
