package com.example.be_medicalbooking.dto.requset;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;
@Data
public class ProfileRequest {
    private String codeUser;

    @NotNull(message = "Không được để trống")
    @NotBlank(message = "Không được khoảng trắng")
    @Size(min = 3, max = 250,message = "ít nhất 3 kí tự và nhiều nhất 250 kí tự")
    private String name;
    private String username;

    @Email(message = "Không đúng định dạng")
    private String email;
    private String password;
    private boolean gender;
    private Date birthday;
    @Pattern(regexp = "^\\d{12}$", message = "không đúng định dạng")
    private String idCard;
    @Pattern(regexp = "^(0|\\+84)[3|5|7|8|9]\\d{8}$",message = "không đúng định dạng")
    private String phone;
    private String address;
    private String urlImage;

    public ProfileRequest() {
    }
}
