package com.example.xyz_hotel.application;

import com.example.xyz_hotel.domain.Room;

import java.util.Date;
import java.util.List;

public class ReservationRequest {
    private Long id;
    private Long userId;
    private Double price;
    private Date date;
    private Boolean isHalfed;
    private List<Room> rooms;
    public ReservationRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
