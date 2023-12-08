package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.UserRepository;
import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.User;
import com.example.xyz_hotel.domain.Wallet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private WalletRepository walletRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEmail("test@test.com");
        testUser.setPassword("Test1@");
        testUser.setPhone("0781881911");
    }

    @Test
    public void loginUser_success() throws Exception {
        when(userRepository.findUserByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/user/login")
                        .param("email", "test@test.com")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"))
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    public void registerUser_Success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Test1");
        userRequest.setLastName("User1");
        userRequest.setEmail("test1@test.com");
        userRequest.setPassword("Test1@");
        userRequest.setNewPassword("Test1@");
        userRequest.setPhone("123456780");

        User savedUser = new User();
        BeanUtils.copyProperties(userRequest, savedUser);
        savedUser.setId(2L);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.firstName").value("Test1"))
                .andExpect(jsonPath("$.email").value("test1@test.com"));
    }

    @Test
    public void getUserInfo_success() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"))
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    public void deleteUser_Success() throws Exception {
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setUser(testUser);
        wallet.setAmount(200.0);

        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(walletRepository.findWalletByUserId(anyLong())).thenReturn(Optional.of(wallet));

        mockMvc.perform(delete("/api/user/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUser_Success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setId(1L);
        userRequest.setFirstName("UpdatedTest");
        userRequest.setLastName("UpdatedUser");
        userRequest.setEmail("updated@test.com");
        userRequest.setPhone("0781881911");

        User updatedUser = new User();
        BeanUtils.copyProperties(userRequest, updatedUser);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("UpdatedTest"))
                .andExpect(jsonPath("$.email").value("updated@test.com"))
                .andExpect(jsonPath("$.phone").value("0781881911"));
    }

    @Test
    public void updateUserPassword_Success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setId(1L);
        userRequest.setPassword("Test1@");
        userRequest.setNewPassword("Test2@");

        User updatedUser = new User();
        BeanUtils.copyProperties(testUser, updatedUser);
        updatedUser.setPassword(userRequest.getNewPassword());

        when(userRepository.findUserByIdAndPassword(anyLong(), anyString())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/user/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.password").value("Test2@"));
    }
}