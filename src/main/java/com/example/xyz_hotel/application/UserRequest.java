package com.example.xyz_hotel.application;

import java.util.regex.Pattern;

public class UserRequest {
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String password;

    public UserRequest() {
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

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public Boolean checkEmail() {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(this.email)
                .matches();
    }

    public Boolean checkPhone() {
        String regexPattern = "^" +
                "(?:(?:\\+|00)33|0)" +
                "\\s*[1-9]" +
                "(?:[\\s.-]*\\d{2}){4}" +
                "$";
        return Pattern.compile(regexPattern)
                .matcher(this.phone)
                .matches();
    }

    public Boolean checkLastName() {
        return this.lastName.length() >= 4 && this.lastName.length() < 30;
    }

    public Boolean checkFirstName() {
        return this.firstName.length() >= 4 && this.firstName.length() < 30;
    }

    public Boolean checkPassword() {
        boolean asUppercase = false;
        boolean asLowercase = false;
        boolean asDigit = false;
        boolean asSpecial = false;

        for (int i = 0; i < this.password.length(); i++) {
            char c = this.password.charAt(i);
            if (Character.isUpperCase(c))
                asUppercase = true;
            else if (Character.isLowerCase(c))
                asLowercase = true;
            else if (Character.isDigit(c))
                asDigit = true;
            if (c >= 33 && c <= 46 || c == 64) {
                asSpecial = true;
            }
        }

        return asSpecial && asDigit && asLowercase && asUppercase;
    }
}
