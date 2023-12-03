package com.example.xyz_hotel.database;

import com.example.xyz_hotel.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findReservationById(Long id);
    List<Reservation> findReservationsByUserId(Long userId);
}
