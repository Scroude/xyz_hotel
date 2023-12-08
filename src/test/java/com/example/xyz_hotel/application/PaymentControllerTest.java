package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.CurrencyRepository;
import com.example.xyz_hotel.database.PaymentRepository;
import com.example.xyz_hotel.database.ReservationRepository;
import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletRepository walletRepository;

    @MockBean
    private CurrencyRepository currencyRepository;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static Reservation testReservation;
    private static User testUser;
    private static Wallet testWallet;
    private static Currency testCurrency;
    private static Payment testPayment;

    public static void setUpUser() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEmail("test@test.com");
        testUser.setPassword("Test1@");
        testUser.setPhone("0781881911");
    }

    public static void setUpWallet() {
        testWallet = new Wallet();
        testWallet.setId(1L);
        testWallet.setUser(testUser);
        testWallet.setAmount(200.0);
    }

    public static void setUpReservation() {
        testReservation = new Reservation();
        testReservation.setId(1L);
        testReservation.setUser(testUser);
        testReservation.setComplete(false);
        testReservation.setPrice(150.00);
        List<Room> rooms = new ArrayList<>();
        rooms.add(Room.STANDARD);
        testReservation.setRooms(rooms);
        testReservation.setHalfed(true);
    }

    public static void setUpCurrency() {
        testCurrency = new Currency();
        testCurrency.setId(1L);
        testCurrency.setCurrency("Euro");
        testCurrency.setPercentage(1);
        testCurrency.setInvPercentage(1);
    }

    @BeforeAll
    public static void setUpPayment() {
        setUpUser();
        setUpWallet();
        setUpReservation();
        setUpCurrency();
        testPayment = new Payment();
        testPayment.setCurrency(testCurrency);
        testPayment.setReservation(testReservation);
        testPayment.setId(1L);
        testPayment.setWallet(testWallet);
        testPayment.setAmount(-75);
    }

    @Test
    public void addPayment_success() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCurrencyId(1L);
        paymentRequest.setWalletId(1L);
        paymentRequest.setReservationId(1L);
        paymentRequest.setAmount((double) -75);

        when(reservationRepository.findById(any(Long.class))).thenReturn(Optional.of(testReservation));
        when(walletRepository.findById(any(Long.class))).thenReturn(Optional.of(testWallet));
        when(currencyRepository.findById(any(Long.class))).thenReturn(Optional.of(testCurrency));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);
        when(walletRepository.save(any(Wallet.class))).thenReturn(testWallet);

        mockMvc.perform(post("/api/payment/add")
                        .content(objectMapper.writeValueAsString(paymentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getPayments_success() throws Exception {
        // Arrange
        Long walletId = 1L;

        List<Payment> payments = Arrays.asList(testPayment);

        when(paymentRepository.findPaymentsByWalletId(walletId)).thenReturn(Optional.of(payments));

        // Act and Assert
        mockMvc.perform(get("/api/payment/" + walletId + "/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(testPayment.getId()))
                .andExpect(jsonPath("$[0].amount").value(testPayment.getAmount()));
    }
}