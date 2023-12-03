package com.example.xyz_hotel.database;

import com.example.xyz_hotel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserByIdAndPassword(Long id, String password);
}
