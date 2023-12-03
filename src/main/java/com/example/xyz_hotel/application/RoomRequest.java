package com.example.xyz_hotel.application;

public class RoomRequest {
    private Long id;
    private String bed;
    private Boolean wifi;
    private String tv;
    private Boolean minibar;
    private Boolean airConditionner;
    private Boolean bathtub;
    private Boolean terrace;
    private Double price;
    public RoomRequest() {
    }

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

    public Boolean getAirConditionner() {
        return airConditionner;
    }

    public void setAirConditionner(Boolean airConditionner) {
        this.airConditionner = airConditionner;
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
