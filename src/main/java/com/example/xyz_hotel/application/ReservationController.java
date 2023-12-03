package com.example.xyz_hotel.application;

import com.example.xyz_hotel.domain.User;
import com.example.xyz_hotel.database.ReservationRepository;
import com.example.xyz_hotel.database.UserRepository;
import com.example.xyz_hotel.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/{userId}/getAll")
    public @ResponseBody List<Reservation> getUserReservation(@PathVariable Long userId) {
        return reservationRepository.findReservationsByUserId(userId);
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addReservation(@RequestBody ReservationRequest reservationRequest) {
        Optional<User> user = userRepository.findById(reservationRequest.getUserId());
        if (user.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setUser(user.get());
            reservation.setHalfed(reservationRequest.getHalfed());
            reservation.setDate(reservationRequest.getDate());
            reservation.setPrice(reservationRequest.getPrice());
            reservationRepository.save(reservation);
            return "Reservation Added";
        } else {
            return "User doesn't exist";
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateReservation(@RequestBody ReservationRequest reservationRequest) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationRequest.getId());
        if (reservation.isPresent()) {
            reservation.get().setDate(reservationRequest.getDate());
            reservation.get().setHalfed(reservationRequest.getHalfed());
            reservation.get().setPrice(reservationRequest.getPrice());
            reservationRepository.save(reservation.get());
            return "Reservation Updated";
        } else {
            return "Reservation doesn't exist";
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteReservation(@PathVariable Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return "Reservation Deleted";
        } else {
            return "Reservation doesn't exist";
        }
    }
}
