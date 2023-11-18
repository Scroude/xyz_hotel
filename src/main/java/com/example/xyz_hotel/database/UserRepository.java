package com.example.xyz_hotel.database;

import com.example.xyz_hotel.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
    User findUserByEmailAndPassword(String email, String password);
    User findUserByIdAndPassword(Long id, String password);
}
