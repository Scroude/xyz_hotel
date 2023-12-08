package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.*;
import com.example.xyz_hotel.domain.*;
import com.example.xyz_hotel.domain.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private WalletRepository walletRepository;

    @MockBean
    private CurrencyRepository currencyRepository;

    @MockBean
    private PaymentRepository paymentRepository;

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
    public void getUserReservations_success() throws Exception {
        // Arrange
        Long userId = 1L;

        List<Reservation> reservations = Arrays.asList(testReservation);

        when(reservationRepository.findReservationsByUserId(userId)).thenReturn(Optional.of(reservations));

        // Act and Assert
        mockMvc.perform(get("/api/reservation/" + userId + "/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(testReservation.getId()))
                .andExpect(jsonPath("$[0].complete").value(testReservation.getComplete()))
                .andExpect(jsonPath("$[0].price").value(testReservation.getPrice()))
                .andExpect(jsonPath("$[0].rooms").value(Room.STANDARD.toString()))
                .andExpect(jsonPath("$[0].halfed").value(testReservation.getHalfed()));
    }

    @Test
    public void addReservation_success() throws Exception {
        // Arrange
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setUserId(1L);
        reservationRequest.setDate(new Date("2001-05-25"));
        reservationRequest.setPrice(150.00);
        List<Room> rooms = new ArrayList<>();
        rooms.add(Room.STANDARD);
        reservationRequest.setRooms(rooms);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(testUser));
        when(walletRepository.findWalletByUserId(any(Long.class))).thenReturn(Optional.of(testWallet));
        when(walletRepository.findById(any(Long.class))).thenReturn(Optional.of(testWallet));
        when(reservationRepository.saveAndFlush(any(Reservation.class))).thenReturn(testReservation);
        when(reservationRepository.findById(any(Long.class))).thenReturn(Optional.of(testReservation));
        when(currencyRepository.findById(any(Long.class))).thenReturn(Optional.of(testCurrency));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);
        when(walletRepository.save(any(Wallet.class))).thenReturn(testWallet);

        // Act & Assert
        mockMvc.perform(post("/api/reservation/add")
                        .content(objectMapper.writeValueAsString(reservationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rooms").value(Room.STANDARD.toString()))
                .andExpect(jsonPath("$.complete").value(false))
                .andExpect(jsonPath("$.halfed").value(true))
                .andExpect(jsonPath("$.price").value(150.00));
    }

    @Test
    public void updateReservation_success() throws Exception {
        // Arrange
        long reservationId = 1L;

        when(reservationRepository.findById(any(Long.class))).thenReturn(Optional.of(testReservation));
        when(walletRepository.findWalletByUserId(any(Long.class))).thenReturn(Optional.of(testWallet));
        when(walletRepository.findById(any(Long.class))).thenReturn(Optional.of(testWallet));
        when(currencyRepository.findById(any(Long.class))).thenReturn(Optional.of(testCurrency));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);
        when(walletRepository.save(any(Wallet.class))).thenReturn(testWallet);

        // Act & Assert
        mockMvc.perform(put("/api/reservation/" + reservationId + "/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteReservation_success() throws Exception {
        // Arrange
        long reservationId = 1L;

        when(reservationRepository.findById(any(Long.class))).thenReturn(Optional.of(testReservation));
        when(walletRepository.findWalletByUserId(any(Long.class))).thenReturn(Optional.of(testWallet));
        when(reservationRepository.existsById(any(Long.class))).thenReturn(true);
        when(currencyRepository.findById(any(Long.class))).thenReturn(Optional.of(testCurrency));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);
        when(walletRepository.save(any(Wallet.class))).thenReturn(testWallet);
        when(walletRepository.findById(any(Long.class))).thenReturn(Optional.of(testWallet));


        // Act & Assert
        mockMvc.perform(delete("/api/reservation/" + reservationId + "/delete")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}