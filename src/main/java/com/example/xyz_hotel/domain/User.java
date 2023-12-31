package com.example.xyz_hotel.domain;

import com.example.xyz_hotel.application.UserRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String lastName;
    @Column(length = 30)
    private String firstName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(length = 20)
    private String phone;
    @Column(nullable = false)
    private String password;

    public User() {
    }

    public User(UserRequest userRequest) {
        this.lastName = userRequest.getLastName();
        this.firstName = userRequest.getFirstName();
        this.email = userRequest.getEmail();
        this.phone = userRequest.getPhone();
        this.password = userRequest.getNewPassword();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
