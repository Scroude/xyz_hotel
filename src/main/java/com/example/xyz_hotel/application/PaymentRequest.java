package com.example.xyz_hotel.application;

import com.example.xyz_hotel.domain.Reservation;


public class PaymentRequest {
    private Long id;
    private Long reservationId;
    private Long walletId;
    private Double amount;

    public PaymentRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservation(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
