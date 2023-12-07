package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.CurrencyRepository;
import com.example.xyz_hotel.domain.Currency;
import com.example.xyz_hotel.exeption.not_found.CurrencyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping(path = "/api/currency")
public class CurrencyController {
    @Autowired
    private CurrencyRepository currencyRepository;

    //Afficher toute les currencies
    @GetMapping(path = "/all")
    public @ResponseBody ResponseEntity<List<Currency>> getCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();
        if (!currencies.isEmpty()) {
            return new ResponseEntity<>(currencies, HttpStatus.OK);
        } else {
            throw new CurrencyNotFoundException();
        }
    }

    //Créée juste pour pouvoir ajouter les currencies facilement
    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<HttpStatus> addCurrencies(@RequestBody List<CurrencyRequest> currencyRequests){
        currencyRequests.forEach(currencyRequest -> {
            Currency currency = new Currency(currencyRequest);
            currencyRepository.save(currency);});
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
