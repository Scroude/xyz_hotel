package com.example.xyz_hotel.domain.payment;

import com.example.xyz_hotel.domain.reservation.Reservation;
import com.example.xyz_hotel.domain.wallet.Wallet;
import jakarta.persistence.*;
import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_reservation", referencedColumnName = "id")
    private Reservation reservation;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wallet", referencedColumnName = "id", nullable = false)
    private Wallet wallet;
    private Double amount;

    public Payment() {}

    public Payment(Long id, Reservation reservation, Wallet wallet, Double amount) {
        this.id = id;
        this.reservation = reservation;
        this.wallet = wallet;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
