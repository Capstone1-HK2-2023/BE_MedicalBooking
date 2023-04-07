package com.example.be_medicalbooking.model.user;

import com.example.be_medicalbooking.model.appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "codeUser")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String codeUser;
    private String name;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private boolean gender;
    private Date birthday;
    private String idCard;
    private String phone;
    private String address;
    private String urlImage;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;
    private int status;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Appointment> appointments= new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
