package com.example.xyz_hotel.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Date date;
    @Column(name = "is_halfed", nullable = false)
    private Boolean isHalfed;
    @Column(nullable = false)
    private Boolean isComplete;
    @Column(nullable = false)
    private List<Room> rooms;

    public Reservation() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getHalfed() {
        return isHalfed;
    }

    public void setHalfed(Boolean halfed) {
        isHalfed = halfed;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }
}
