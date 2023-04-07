package com.example.be_medicalbooking.dto.requset;

import lombok.Data;

@Data
public class SignUpForm {
    private String name;
    private String username;
    private String email;
    private String password;
    private int otp;
}