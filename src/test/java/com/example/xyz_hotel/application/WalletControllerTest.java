package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.Wallet;
import com.example.xyz_hotel.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletRepository walletRepository;

    private static User testUser;
    private static Wallet testWallet;

    private static void setUpUser() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEmail("test@test.com");
        testUser.setPassword("Test1@");
        testUser.setPhone("0781881911");
    }

    @BeforeAll
    public static void setUpWallet() {
        setUpUser();
        testWallet = new Wallet();
        testWallet.setId(1L);
        testWallet.setUser(testUser);
        testWallet.setAmount(200.0);
    }

    @Test
    void getUserWalletTest() throws Exception {
        Long userId = 1L;

        when(walletRepository.findWalletByUserId(userId)).thenReturn(Optional.of(testWallet));

        // Act and Assert
        mockMvc.perform(get("/api/wallet/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testWallet.getId()))
                .andExpect(jsonPath("$.amount").value(testWallet.getAmount()));
    }
}