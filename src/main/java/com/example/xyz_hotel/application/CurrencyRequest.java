package com.example.xyz_hotel.application;

public class CurrencyRequest {
    private Long id ;

    private String currency;

    private double percentage;

    private double invPercentage;

    public CurrencyRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
