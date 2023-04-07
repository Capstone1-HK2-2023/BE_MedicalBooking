package com.example.be_medicalbooking.dto.requset;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ChangePassword {
    private String password;
    @Size(min = 6,max = 250, message = "Phải ít nhất 6 và nhiều nhất 250")
    private String newPassword;
}
