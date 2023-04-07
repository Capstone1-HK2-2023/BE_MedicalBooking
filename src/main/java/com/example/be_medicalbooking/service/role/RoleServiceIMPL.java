package com.example.be_medicalbooking.service.role;

import com.example.be_medicalbooking.model.user.ERole;
import com.example.be_medicalbooking.model.user.Role;
import com.example.be_medicalbooking.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceIMPL implements IRoleService{
    @Autowired
    IRoleRepository repository;
    @Override
    public Optional<Role> findByName(ERole name) {
        return repository.findByName(name);
    }
}