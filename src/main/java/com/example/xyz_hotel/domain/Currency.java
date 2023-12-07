package com.example.xyz_hotel.domain;

import com.example.xyz_hotel.application.CurrencyRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String currency;
    @Column(nullable = false)
    private double percentage;
    @Column(nullable = false)
    private double invPercentage;

    public Currency(CurrencyRequest currencyRequest) {
        this.currency = currencyRequest.getCurrency();
        this.percentage = currencyRequest.getPercentage();
        this.invPercentage = currencyRequest.getInvPercentage();
    }

    public Currency() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getInvPercentage() {
        return invPercentage;
    }

    public void setInvPercentage(double invPercentage) {
        this.invPercentage = invPercentage;
    }
}
