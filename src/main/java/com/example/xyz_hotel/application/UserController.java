package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.CurrencyRepository;
import com.example.xyz_hotel.database.UserRepository;
import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.User;
import com.example.xyz_hotel.domain.Currency;
import com.example.xyz_hotel.domain.Wallet;
import com.example.xyz_hotel.exeption.not_found.CurrencyNotFoundException;
import com.example.xyz_hotel.exeption.not_found.UserNotFoundException;
import com.example.xyz_hotel.exeption.not_found.WalletNotFoundException;
import com.example.xyz_hotel.exeption.nulls.NullUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private CurrencyRepository currencyRepository;

    //Enregistrer un nouveau utilisateur
    @PostMapping(path = "/register")
    public @ResponseBody ResponseEntity<User> addNewUser(@RequestBody UserRequest userRequest) {
        //Vérification des données
        if (!userRequest.checkLastName()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "LastName is incorrect");
        if (!userRequest.checkFirstName()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FirstName is incorrect");
        if (!userRequest.checkEmail()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email is incorrect");
        if (!userRequest.checkPhone()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Phone is incorrect");
        if (!userRequest.checkPassword()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password is incorrect");

        //Récupération de la currency
        Currency currency = currencyRepository.findById(1L)
                .orElseThrow(CurrencyNotFoundException::new);

        User user = new User(userRequest);
        user = userRepository.save(user);
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setAmount((double) 0);
        walletRepository.save(wallet);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Connexion
    @GetMapping(path = "/login")
    public @ResponseBody ResponseEntity<User> getUser(@RequestParam String email, @RequestParam String password) {
        if (email == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email can not be null");
        if (password == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password can not be null");
        User user = userRepository.findUserByEmailAndPassword(email, password)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong login information"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Récupérer les infos d'un utilisateur
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<User> getUserInfo(@PathVariable Long id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id can not be null");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setPassword("");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Supprimer un utilisateur
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {

        if (userRepository.existsById(id)) {
            //Récupération et suppression du wallet
            Wallet wallet = walletRepository.findWalletByUserId(id)
                    .orElseThrow(WalletNotFoundException::new);
            walletRepository.deleteById(wallet.getId());
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new UserNotFoundException();
        }
    }

    //Mettre à jour les infos d'un utilisateur
    @PutMapping(path ="/update")
    public @ResponseBody ResponseEntity<User> updateUser(@RequestBody UserRequest userRequest) {
        //Vérification des informations
        if (userRequest.getId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id can not be null");
        if (!userRequest.checkLastName()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "LastName is incorrect");
        if (!userRequest.checkFirstName()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FirstName is incorrect");
        if (!userRequest.checkEmail()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email is incorrect");
        if (!userRequest.checkPhone()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Phone is incorrect");

        //Récupération de l'utilisateur
        User user = userRepository.findById(userRequest.getId())
                .orElseThrow(UserNotFoundException::new);

        //Sauvegarde des données
        user.setEmail(userRequest.getEmail());
        user.setLastName(userRequest.getLastName());
        user.setFirstName(userRequest.getFirstName());
        user.setPhone(userRequest.getPhone());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Mise à jour du mot de passe
    @PutMapping(path ="/updatePassword")
    public @ResponseBody ResponseEntity<User> updateUserPassword(@RequestBody ChangeUserPassword changeUserPassword) {
        if (changeUserPassword.getId() == null) throw new NullUserException();

        //On vérifie l'ancien mot de passe
        if (!changeUserPassword.checkPassword()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password is incorrect");
        User user = userRepository.findUserByIdAndPassword(changeUserPassword.getId(), changeUserPassword.getOldPassword())
                .orElseThrow(UserNotFoundException::new);

        //Validation du nouveau mot de passe
        if (!changeUserPassword.checkPassword()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password is incorrect");

        user.setPassword(changeUserPassword.getNewPassword());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
