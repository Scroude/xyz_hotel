package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.UserRepository;
import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.User;
import com.example.xyz_hotel.domain.Wallet;
import com.example.xyz_hotel.exeption.not_found.UserNotFoundException;
import com.example.xyz_hotel.exeption.not_found.WalletNotFoundException;
import com.example.xyz_hotel.exeption.nulls.NullUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;

    //Register user
    @PostMapping(path = "/register")
    public @ResponseBody ResponseEntity<User> addNewUser(@RequestBody UserRequest userRequest) {
        //Check the data
        if (!userRequest.checkLastName() && userRequest.getLastName() == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "LastName doesn't match requirement");
        if (!userRequest.checkFirstName() && userRequest.getFirstName() == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FirstName doesn't match requirement");
        if (!userRequest.checkEmail() && userRequest.getEmail() == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email doesn't match requirement");
        if (!userRequest.checkPhone() && userRequest.getPhone() == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Phone doesn't match requirement");
        if (!userRequest.checkPassword() && userRequest.getNewPassword() == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password doesn't match requirement");

        //Create the user
        User user = new User(userRequest);
        user = userRepository.save(user);

        //Create the wallet
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setAmount((double) 0);
        walletRepository.save(wallet);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Login
    @PostMapping(path = "/login")
    public @ResponseBody ResponseEntity<String> getUser(@RequestBody UserRequest userRequest) {
        //Check the data
        if (userRequest.getEmail() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email can not be null");
        if (userRequest.getPassword() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password can not be null");

        //Get the user
        User user = userRepository.findUserByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong login information"));
        return new ResponseEntity<>(user.getId().toString(), HttpStatus.OK);
    }

    //Get users infos
    @GetMapping(path = "/{userId}")
    public @ResponseBody ResponseEntity<User> getUserInfo(@PathVariable Long userId) {
        //Check the data
        if (userId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id can not be null");

        //Get the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setPassword("");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Delete user
    @DeleteMapping(path = "/{userId}/delete")
    public @ResponseBody ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userId) {

        if (userRepository.existsById(userId)) {
            //Récupération et suppression du wallet
            Wallet wallet = walletRepository.findWalletByUserId(userId)
                    .orElseThrow(WalletNotFoundException::new);
            walletRepository.deleteById(wallet.getId());
            userRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new UserNotFoundException();
        }
    }

    //Update user
    @PutMapping(path ="/update")
    public @ResponseBody ResponseEntity<Boolean> updateUser(@RequestBody UserRequest userRequest) {
        //Check the data
        if (userRequest.getId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id can not be null");
        if (!userRequest.checkLastName() && userRequest.getLastName() == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "LastName doesn't match requirement");
        if (!userRequest.checkFirstName() && userRequest.getFirstName() == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FirstName doesn't match requirement");
        if (!userRequest.checkEmail() && userRequest.getEmail() == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email doesn't match requirement");
        if (!userRequest.checkPhone() && userRequest.getPhone() == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Phone doesn't match requirement");

        //Get the user
        User user = userRepository.findById(Long.parseLong(userRequest.getId()))
                .orElseThrow(UserNotFoundException::new);

        //Saving information
        user.setEmail(userRequest.getEmail());
        user.setLastName(userRequest.getLastName());
        user.setFirstName(userRequest.getFirstName());
        user.setPhone(userRequest.getPhone());
        userRepository.save(user);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    //Update users password
    @PutMapping(path ="/updatePassword")
    public @ResponseBody ResponseEntity<User> updateUserPassword(@RequestBody UserRequest userRequest) {
        //Check the data
        if (userRequest.getId() == null) throw new NullUserException();
        if (userRequest.getPassword() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is null");
        if (userRequest.getNewPassword() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password is null");

        //Check the old password by getting the user
        User user = userRepository.findUserByIdAndPassword(Long.parseLong(userRequest.getId()), userRequest.getPassword())
                .orElseThrow(UserNotFoundException::new);

        //Check the new password
        if (!userRequest.checkPassword()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password doesn't match requirements");

        //Save the new password
        user.setPassword(userRequest.getNewPassword());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
