package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.CurrencyRepository;
import com.example.xyz_hotel.database.PaymentRepository;
import com.example.xyz_hotel.database.ReservationRepository;
import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.Currency;
import com.example.xyz_hotel.domain.Payment;
import com.example.xyz_hotel.domain.Reservation;
import com.example.xyz_hotel.domain.Wallet;
import com.example.xyz_hotel.exeption.not_found.CurrencyNotFoundException;
import com.example.xyz_hotel.exeption.not_found.PaymentNotFoundException;
import com.example.xyz_hotel.exeption.not_found.ReservationNotFoundException;
import com.example.xyz_hotel.exeption.not_found.WalletNotFoundException;
import com.example.xyz_hotel.exeption.nulls.NullCurrencyException;
import com.example.xyz_hotel.exeption.nulls.NullReservationException;
import com.example.xyz_hotel.exeption.nulls.NullWalletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private CurrencyRepository currencyRepository;


    //Créer un paiement
    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<Payment> addPayment(@RequestBody PaymentRequest paymentRequest) {
        Payment payment = new Payment();

        //Récupération de la réservation
        if (paymentRequest.getReservationId() != null) {
            Reservation reservation = reservationRepository.findById(paymentRequest.getReservationId())
                    .orElseThrow(ReservationNotFoundException::new);
            payment.setReservation(reservation);
        }

        //Récupération du wallet
        if (paymentRequest.getWalletId() == null) throw new NullWalletException();
        Wallet wallet = walletRepository.findById(paymentRequest.getWalletId())
                .orElseThrow(WalletNotFoundException::new);

        //Récupération de la currency
        if (paymentRequest.getCurrencyId() == null) throw new NullCurrencyException();
        Currency currency = currencyRepository.findById(paymentRequest.getCurrencyId())
                .orElseThrow(CurrencyNotFoundException::new);

        //On vérifi que l'utilisateur à les fonds suffisant
        if (paymentRequest.getAmount() < 0) {
            if ((wallet.getAmount() + paymentRequest.getAmount() < 0)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unsufficient balance");
        }

        //On converti le montant en euro si nécessaire
        if (currency.getId() != 1) {
            paymentRequest.setAmount(round(paymentRequest.getAmount() * currency.getInvPercentage(), 2));
        }

        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(currency);
        payment.setWallet(wallet);
        paymentRepository.save(payment);

        wallet.setAmount(round(wallet.getAmount() + paymentRequest.getAmount(), 2));
        walletRepository.save(wallet);

        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    //Afficher les paiements d'un utilisateur
    @GetMapping(path = "/{walletId}/all")
    public @ResponseBody ResponseEntity<List<Payment>> getUserPayments(@PathVariable Long walletId) {
        if (walletId == null) throw new NullWalletException();
        Optional<List<Payment>> payments = paymentRepository.findPaymentsByWalletId(walletId);
        return payments.map(_payments -> new ResponseEntity<>(_payments, HttpStatus.OK))
                .orElseThrow(PaymentNotFoundException::new);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
