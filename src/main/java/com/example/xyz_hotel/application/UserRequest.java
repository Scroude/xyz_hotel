package com.example.xyz_hotel.application;

import java.util.regex.Pattern;

public class UserRequest {
    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String password;
    private String newPassword;

    public UserRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        //If firstname length is between 4 and 30
        if (this.lastName.length() >= 4 && this.lastName.length() < 30) {
            //Check if the string contains digits or special characters
            for (int i = 0; i < this.lastName.length(); i++) {
                char c = this.lastName.charAt(i);
                if (Character.isDigit(c)) {
                    return false;
                }
                if (c >= 33 && c <= 46 || c == 64) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public Boolean checkFirstName() {
        //If firstname length is between 4 and 30
        if (this.firstName.length() >= 4 && this.firstName.length() < 30) {
            //Check if the string contains digits or special characters
            for (int i = 0; i < this.firstName.length(); i++) {
                char c = this.firstName.charAt(i);
                if (Character.isDigit(c)) {
                    return false;
                }
                if (c >= 33 && c <= 46 || c == 64) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public Boolean checkPassword() {
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < this.newPassword.length(); i++) {
            char c = this.newPassword.charAt(i);
            if (Character.isUpperCase(c))
                hasUppercase = true;
            else if (Character.isLowerCase(c))
                hasLowercase = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            if (c >= 33 && c <= 46 || c == 64) {
                hasSpecial = true;
            }
        }

        return hasSpecial && hasDigit && hasLowercase && hasUppercase;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
