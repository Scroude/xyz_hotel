package com.example.xyz_hotel.domain.reservation;

import com.example.xyz_hotel.domain.user.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private User user;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Date date;
    @Column(name = "is_halfed", nullable = false)
    private Boolean isHalfed;

    public Reservation() {
    }
    public Reservation(Long id, Boolean isHalfed) {
        this.id = id;
        this.isHalfed = isHalfed;
    }

    public Reservation(Long id, User user, Double price, Date date, Boolean isHalfed) {
        this.id = id;
        this.user = user;
        this.price = price;
        this.date = date;
        this.isHalfed = isHalfed;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getHalfed() {
        return isHalfed;
    }

    public void setHalfed(Boolean halfed) {
        isHalfed = halfed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
