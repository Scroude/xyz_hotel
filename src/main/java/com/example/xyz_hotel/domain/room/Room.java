package com.example.xyz_hotel.domain.room;

import jakarta.persistence.*;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String bed;
    @Column(nullable = false)
    private Boolean wifi;
    @Column(nullable = false, length = 50)
    private String tv;
    @Column(nullable = false)
    private Boolean minibar;
    @Column(nullable = false)
    private Boolean air_conditionner;
    @Column(nullable = false)
    private Boolean bathtub;
    @Column(nullable = false)
    private Boolean terrace;
    @Column(nullable = false)
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public Boolean getMinibar() {
        return minibar;
    }

    public void setMinibar(Boolean minibar) {
        this.minibar = minibar;
    }

    public Boolean getAir_conditionner() {
        return air_conditionner;
    }

    public void setAir_conditionner(Boolean air_conditionner) {
        this.air_conditionner = air_conditionner;
    }

    public Boolean getBathtub() {
        return bathtub;
    }

    public void setBathtub(Boolean bathtub) {
        this.bathtub = bathtub;
    }

    public Boolean getTerrace() {
        return terrace;
    }

    public void setTerrace(Boolean terrace) {
        this.terrace = terrace;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
