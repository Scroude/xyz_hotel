package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.User;
import com.example.xyz_hotel.database.ReservationRepository;
import com.example.xyz_hotel.database.UserRepository;
import com.example.xyz_hotel.domain.Reservation;
import com.example.xyz_hotel.domain.Wallet;
import com.example.xyz_hotel.exeption.not_found.ReservationNotFoundException;
import com.example.xyz_hotel.exeption.not_found.UserNotFoundException;
import com.example.xyz_hotel.exeption.not_found.WalletNotFoundException;
import com.example.xyz_hotel.exeption.nulls.NullReservationException;
import com.example.xyz_hotel.exeption.nulls.NullUserException;
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
@RequestMapping(path = "/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private PaymentController paymentController;

    //Récupérer les réservations d'un utilisateur
    @GetMapping(path = "/{userId}/all")
    public @ResponseBody ResponseEntity<List<Reservation>> getUserReservations(@PathVariable Long userId) {
        if (userId == null) throw new NullUserException();

        //Récupération des réservations du l'utilisateur
        Optional<List<Reservation>> reservations = reservationRepository.findReservationsByUserId(userId);
        return reservations.map(_reservations -> new ResponseEntity<>(_reservations, HttpStatus.OK))
                .orElseThrow(ReservationNotFoundException::new);
    }

    //Créer une réservation
    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationRequest) {
        if (reservationRequest.getUserId() == null) throw new NullUserException();
        if (reservationRequest.getPrice() == null || reservationRequest.getDate() == null || reservationRequest.getRooms() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date or price or rooms can not be null");

        //Récupération de l'utilisateur
        User user = userRepository.findById(reservationRequest.getUserId())
                .orElseThrow(UserNotFoundException::new);

        //Création de la réservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setComplete(false);
        reservation.setDate(reservationRequest.getDate());
        reservation.setPrice(reservationRequest.getPrice());
        reservation.setRooms(reservationRequest.getRooms());

        // Récupératon du wallet
        Wallet wallet = walletRepository.findWalletByUserId(reservationRequest.getUserId())
                .orElseThrow(WalletNotFoundException::new);

        //L'utilisateur paye la moitié
        if (wallet.getAmount() + (reservationRequest.getPrice() / -2) > 0) {
            reservation.setHalfed(true);
            reservation = reservationRepository.saveAndFlush(reservation);
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAmount(round(reservation.getPrice() / -2, 2));
            paymentRequest.setWalletId(wallet.getId());
            paymentRequest.setReservationId(reservation.getId());
            paymentRequest.setCurrencyId(1L);
            paymentController.addPayment(paymentRequest);

            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unsufficient balance");
        }
    }

    //Mettre à jour une réservation
    @PutMapping(path = "/{reservationId}/update")
    public @ResponseBody ResponseEntity<Reservation> updateReservation(@PathVariable Long reservationId) {
        //Récupération de la réservation
        if (reservationId == null) throw new NullReservationException();
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        //Récupération du wallet
        Wallet wallet = walletRepository.findWalletByUserId(reservation.getUser().getId())
                .orElseThrow(WalletNotFoundException::new);

        //Si l'utilisateur a déjà confirmé la réservation
        if (reservation.getHalfed() && !reservation.getComplete()) {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAmount(round(reservation.getPrice() / -2, 2));
            paymentRequest.setWalletId(wallet.getId());
            paymentRequest.setReservationId(reservation.getId());
            paymentRequest.setCurrencyId(1L);
            paymentController.addPayment(paymentRequest);
            reservation.setComplete(true);
        }
        reservationRepository.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    //Annuler une réservation
    @PutMapping(path = "/{reservationId}/cancel")
    public @ResponseBody ResponseEntity<Reservation> deleteReservation(@PathVariable Long reservationId) {
        //Récupération de la réservation
        if (reservationId == null) throw new NullReservationException();
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new );

        //Refund if necessary
        if (reservation.getHalfed() && !reservation.getComplete()) { //Half
            //Get the wallet
            Wallet wallet = walletRepository.findWalletByUserId(reservation.getUser().getId())
                    .orElseThrow(WalletNotFoundException::new);

            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAmount(round(reservation.getPrice() / 2, 2));
            paymentRequest.setWalletId(wallet.getId());
            paymentRequest.setReservationId(reservation.getId());
            paymentRequest.setCurrencyId(1L);
            paymentController.addPayment(paymentRequest);
            reservation.setHalfed(false);
        } else if (reservation.getComplete() && reservation.getHalfed()) { //Full
            //On récupère le wallet
            Wallet wallet = walletRepository.findWalletByUserId(reservation.getUser().getId())
                    .orElseThrow(WalletNotFoundException::new);

            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAmount(reservation.getPrice());
            paymentRequest.setWalletId(wallet.getId());
            paymentRequest.setReservationId(reservation.getId());
            paymentRequest.setCurrencyId(1L);
            paymentController.addPayment(paymentRequest);
            reservation.setHalfed(false);
            reservation.setComplete(false);
        }

        reservationRepository.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
