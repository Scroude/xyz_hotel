package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.CurrencyRepository;
import com.example.xyz_hotel.domain.Currency;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyRepository currencyRepository;

    private static Currency testCurrency;

    @BeforeAll
    public static void setUpCurrency() {
        testCurrency = new Currency();
        testCurrency.setId(1L);
        testCurrency.setCurrency("Euro");
        testCurrency.setPercentage(1);
        testCurrency.setInvPercentage(1);
    }

    @Test
    public void getCurrencies_success() throws Exception {
        List<Currency> currencies = Arrays.asList(testCurrency);

        when(currencyRepository.findAll()).thenReturn(currencies);

        // Act and Assert
        mockMvc.perform(get("/api/currency/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(testCurrency.getId()))
                .andExpect(jsonPath("$[0].currency").value(testCurrency.getCurrency()))
                .andExpect(jsonPath("$[0].percentage").value(testCurrency.getPercentage()))
                .andExpect(jsonPath("$[0].invPercentage").value(testCurrency.getInvPercentage()));
    }
}