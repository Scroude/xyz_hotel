package com.example.xyz_hotel.domain.reservation;

import com.example.xyz_hotel.database.ReservationRepository;
import com.example.xyz_hotel.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(name = "/reservation")
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping(path = "/user")
    public @ResponseBody List<Reservation> getUserReservation(@RequestBody User user) {
        return reservationRepository.findReservationsByUser(user);
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateReservation(@RequestBody Reservation reservation) {
        if (reservationRepository.findReservationById(reservation.getId()) != null) {
            reservationRepository.save(reservation);
            return "Reservation Updated";
        } else {
            return "Reservation doesn't exist";
        }
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteReservation(@RequestBody Reservation reservation) {
        if (reservationRepository.findById(reservation.getId()).isPresent()) {
            reservationRepository.deleteById(reservation.getId());
            return "Reservation Deleted";
        } else {
            return "Reservation doesn't exist";
        }
    }
}
