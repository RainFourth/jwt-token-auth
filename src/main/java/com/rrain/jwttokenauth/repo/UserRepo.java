package com.rrain.jwttokenauth.repo;

import com.rrain.jwttokenauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findUserByUsername(String username);
}
