package com.example.xyz_hotel.application;

public class ChangeUserPassword {
    private Long id;
    private String oldPassword;
    private String newPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public Boolean checkPassword() {
        boolean asUppercase = false;
        boolean asLowercase = false;
        boolean asDigit = false;
        boolean asSpecial = false;

        for (int i = 0; i < this.newPassword.length(); i++) {
            char c = this.newPassword.charAt(i);
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
