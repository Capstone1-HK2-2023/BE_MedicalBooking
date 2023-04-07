package com.example.be_medicalbooking.service.user;

import com.example.be_medicalbooking.model.user.User;
import com.example.be_medicalbooking.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceIMPL implements IUserService{
   @Autowired
    IUserRepository userRepository;
    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    public User createUser(User user){
        User createUser=userRepository.save(user);
        createUser.setCodeUser(String.format("user%08d",createUser.getId()));
        userRepository.save(createUser);

        return createUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}