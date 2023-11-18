package com.example.xyz_hotel.domain.user;

import com.example.xyz_hotel.database.UserRepository;
import com.example.xyz_hotel.database.WalletRepository;
import com.example.xyz_hotel.domain.wallet.Currency;
import com.example.xyz_hotel.domain.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;

    @PostMapping(path = "/register")
    public @ResponseBody String addNewUser(@RequestBody User user) {
        User newUser = userRepository.save(user);
        Wallet wallet = new Wallet();
        wallet.setUser(newUser);
        wallet.setAmount((double) 0);
        wallet.setCurrency(String.valueOf(Currency.Euro));
        walletRepository.save(wallet);
        return "User and Wallet Created";
    }

    @GetMapping(path = "/login")
    public @ResponseBody User getUser(@RequestBody User user) {
        return userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @GetMapping(path = "/info")
    public @ResponseBody User getUserInfo(@RequestBody User user) {
        return userRepository.findUserById(user.getId());
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteUser(@RequestBody User user) {
        if (userRepository.findUserByIdAndPassword(user.getId(), user.getPassword()) != null) {
            walletRepository.deleteWalletByUser(user);
            userRepository.deleteById(user.getId());
            return "User Deleted";
        } else {
            return "Incorrect Password";
        }
    }

    @PutMapping(path ="/update")
    public @ResponseBody String updateUser(@RequestBody User user) {
        if (userRepository.findUserByIdAndPassword(user.getId(), user.getPassword()) != null) {
            userRepository.save(user);
            return "User Updated";
        } else {
            return "User doesn't exist";
        }
    }

    @PutMapping(path ="/updatePassword")
    public @ResponseBody String updateUserPassword(@RequestBody ChangeUserPassword changeUserPassword) {
        if (userRepository.findUserByIdAndPassword(changeUserPassword.getId(), changeUserPassword.getOldPassword()) != null) {
            User user = new User(changeUserPassword.getId(), changeUserPassword.getNewPassword());
            userRepository.save(user);
            return "Password Updated";
        }
        return "Incorrect Password";
    }
}
