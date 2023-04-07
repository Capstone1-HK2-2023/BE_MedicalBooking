package com.example.be_medicalbooking.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Profile {
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
}
