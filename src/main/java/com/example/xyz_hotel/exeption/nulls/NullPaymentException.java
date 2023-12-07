package com.example.xyz_hotel.exeption.nulls;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Payment id is null")
public class NullPaymentException extends RuntimeException{
}
