package com.example.xyz_hotel.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private User user;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private String currency;

    public Wallet() {
    }
    public Wallet(Long id) {
        this.id = id;
    }

    public Wallet(Long id, User user, Double amount, String currency) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
