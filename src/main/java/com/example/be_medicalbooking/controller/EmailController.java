package com.example.be_medicalbooking.controller;

import com.example.be_medicalbooking.dto.reponse.ResponseMessage;
import com.example.be_medicalbooking.dto.requset.SignUpForm;
import com.example.be_medicalbooking.model.user.User;
import com.example.be_medicalbooking.service.user.IUserService;
import com.example.be_medicalbooking.utils.EmailService;
import com.example.be_medicalbooking.utils.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private OTPService otpService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IUserService iUserService;
    @GetMapping("/getOtpRegister")
    public ResponseEntity<ResponseMessage> getOtpRegister(@RequestParam String email) {
        if (iUserService.existsByEmail(email)==false) {
            int otp = otpService.generateOTP(email);
            String message = "<h3>Otp</h3>"
                    + "<p>Mã OTP để đăng ký tài khoản của bạn là: " + otp + ".</p>"
                    + "<p>Mã OTP có hiệu lực trong thời gian 4 phút.</p>";
            if(emailService.sendEmail(email, "Medical booking", message))
                return new ResponseEntity<>(new ResponseMessage(String.valueOf(otp)), HttpStatus.OK);
            return new ResponseEntity<>(new ResponseMessage("Gửi email bị lỗi"), HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(new ResponseMessage("Email đã tồn tại trong hệ thống"), HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getOtpForgotPassword")
    public ResponseEntity<?> getOtpForgotPassword(@RequestParam String email) {
        if(iUserService.existsByEmail(email)==true){
            User user= iUserService.findByEmail(email).get();
            int otp = otpService.generateOTP(email);
            String message = "<h3>Otp</h3>"
                    + "<p>Mã OTP để đăng ký tài khoản của bạn là: " + otp + ".</p>"
                    + "<p>Mã OTP có hiệu lực trong thời gian 4 phút.</p>";
            SignUpForm signUpForm = new SignUpForm();
            signUpForm.setEmail(user.getEmail());
            signUpForm.setUsername(user.getUsername());
            signUpForm.setOtp(otp);
            if(emailService.sendEmail(email, "Medical booking", message))
                return new ResponseEntity<>(signUpForm, HttpStatus.OK);
            return new ResponseEntity<>(new ResponseMessage("Gửi email bị lỗi"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Email không tồn tại"), HttpStatus.BAD_REQUEST);
    }
}
