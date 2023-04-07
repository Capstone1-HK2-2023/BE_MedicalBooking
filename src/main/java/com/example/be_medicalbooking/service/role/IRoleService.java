package com.example.be_medicalbooking.service.role;


import com.example.be_medicalbooking.model.user.ERole;
import com.example.be_medicalbooking.model.user.Role;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(ERole name);
}