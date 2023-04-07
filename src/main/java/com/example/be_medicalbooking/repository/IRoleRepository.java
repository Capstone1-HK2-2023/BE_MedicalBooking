package com.example.be_medicalbooking.repository;

import com.example.be_medicalbooking.model.user.ERole;
import com.example.be_medicalbooking.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}