package com.example.be_medicalbooking.service.user;

import com.example.be_medicalbooking.model.user.User;

import java.util.Optional;

public interface IUserService {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    User save(User user);
    User createUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
