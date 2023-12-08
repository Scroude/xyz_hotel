package com.example.xyz_hotel.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRequestTest {
    
    private final UserRequest userRequest = new UserRequest();

    @Test
    void checkEmail() {
        // Set email and check for correct validity
        userRequest.setEmail("test@test.com");
        Assertions.assertTrue(userRequest.checkEmail());

        // Set email and check for correct validity
        userRequest.setEmail("Test.@test.com");
        Assertions.assertFalse(userRequest.checkEmail());
    }

    @Test
    void checkPhone() {
        // Set phone and check for correct validity
        userRequest.setPhone("+33 7 81 88 19 11");
        Assertions.assertTrue(userRequest.checkPhone());

        // Set phone and check for correct validity
        userRequest.setPhone("0781881911");
        Assertions.assertTrue(userRequest.checkPhone());

        // Set phone and check for correct validity
        userRequest.setPhone("781881911");
        Assertions.assertFalse(userRequest.checkPhone());
    }

    @Test
    void checkLastName() {
        // Set lastname and check for correct validity
        userRequest.setLastName("test");
        Assertions.assertTrue(userRequest.checkLastName());

        // Set password and check for correct validity
        userRequest.setLastName("Aaa@");
        Assertions.assertFalse(userRequest.checkLastName());

        // Set password and check for correct validity
        userRequest.setLastName("Aaa1");
        Assertions.assertFalse(userRequest.checkLastName());
    }

    @Test
    void checkFirstName() {
        // Set lastname and check for correct validity
        userRequest.setFirstName("test");
        Assertions.assertTrue(userRequest.checkFirstName());

        // Set password and check for correct validity
        userRequest.setFirstName("Aaa@");
        Assertions.assertFalse(userRequest.checkFirstName());

        // Set password and check for correct validity
        userRequest.setFirstName("Aaa1");
        Assertions.assertFalse(userRequest.checkFirstName());
    }

    @Test
    void checkPassword() {
        // Set password and check for correct validity 
        userRequest.setNewPassword("Aa1@");
        Assertions.assertTrue(userRequest.checkPassword());

        // Set password and check for correct validity 
        userRequest.setNewPassword("password");
        Assertions.assertFalse(userRequest.checkPassword());

        // Set password and check for correct validity 
        userRequest.setNewPassword("PASSWORD");
        Assertions.assertFalse(userRequest.checkPassword());

        // Set password and check for correct validity 
        userRequest.setNewPassword("Password123");
        Assertions.assertFalse(userRequest.checkPassword());

        // Set password and check for correct validity 
        userRequest.setNewPassword("Password123@");
        Assertions.assertTrue(userRequest.checkPassword());
    }
}