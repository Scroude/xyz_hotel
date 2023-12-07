package com.example.xyz_hotel.exeption.not_found;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Wallet")
public class WalletNotFoundException extends RuntimeException{
}
