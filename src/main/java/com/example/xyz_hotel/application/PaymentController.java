package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.PaymentRepository;
import com.example.xyz_hotel.database.ReservationRepository;
import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private WalletRepository walletRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addPayment(@RequestBody PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        if (reservationRepository.existsById(paymentRequest.getReservationId())) {
            payment.setReservation(reservationRepository.findById(paymentRequest.getReservationId()).get());
        }
        payment.setAmount(paymentRequest.getAmount());
        if (walletRepository.existsById(paymentRequest.getWalletId())) {
            payment.setWallet(walletRepository.findById(paymentRequest.getWalletId()).get());
        }
        paymentRepository.save(payment);
        return "Payment added";
    }

    @GetMapping(path = "/{walletId}/getAll")
    public @ResponseBody List<Payment> getPayments(@PathVariable Long walletId) {
        return paymentRepository.findPaymentsByWalletId(walletId);
    }
}
