package com.example.xyz_hotel.domain.payment;

import com.example.xyz_hotel.database.PaymentRepository;
import com.example.xyz_hotel.domain.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addPayment(@RequestBody Payment payment) {
        paymentRepository.save(payment);
        return "Payment added";
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody List<Payment> getPayments(@RequestBody Wallet wallet) {
        return paymentRepository.findPaymentsByWallet(wallet);
    }
}
