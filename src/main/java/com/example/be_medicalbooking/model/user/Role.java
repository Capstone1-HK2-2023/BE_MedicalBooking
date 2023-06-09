package com.example.be_medicalbooking.model.user;

import lombok.Data;

import javax.persistence.*;
@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
