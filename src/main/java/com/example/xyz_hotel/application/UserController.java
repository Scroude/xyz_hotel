package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.UserRepository;
import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.User;
import com.example.xyz_hotel.domain.Currency;
import com.example.xyz_hotel.domain.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;

    @PostMapping(path = "/register")
    public @ResponseBody String addNewUser(@RequestBody UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());
        user = userRepository.save(user);
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setAmount((double) 0);
        wallet.setCurrency(String.valueOf(Currency.Euro));
        walletRepository.save(wallet);
        return "User and Wallet Created";
    }

    @GetMapping(path = "/login")
    public @ResponseBody Optional<User> getUser(@RequestParam String email, @RequestParam String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<User> getUserInfo(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            Wallet wallet = walletRepository.findWalletByUserId(id);
            walletRepository.deleteById(wallet.getId());
            userRepository.deleteById(id);
            return "User and Wallet Deleted";
        } else {
            return "Incorrect Password";
        }
    }

    @PutMapping(path ="/update")
    public @ResponseBody String updateUser(@RequestBody UserRequest userRequest) {
        Optional<User> user = userRepository.findById(userRequest.getId());
        if (user.isPresent()) {
            user.get().setEmail(userRequest.getEmail());
            user.get().setLastName(userRequest.getLastName());
            user.get().setFirstName(userRequest.getFirstName());
            user.get().setPhone(userRequest.getPhone());
            userRepository.save(user.get());
            return "User Updated";
        } else {
            return "User doesn't exist";
        }
    }

    @PutMapping(path ="/updatePassword")
    public @ResponseBody String updateUserPassword(@RequestBody ChangeUserPassword changeUserPassword) {
        if (userRepository.findUserByIdAndPassword(changeUserPassword.getId(), changeUserPassword.getOldPassword()).isPresent()) {
            User user = userRepository.findById(changeUserPassword.getId()).get();
            user.setPassword(changeUserPassword.getNewPassword());
            userRepository.save(user);
            return "Password Updated";
        }
        return "Incorrect Password";
    }
}
