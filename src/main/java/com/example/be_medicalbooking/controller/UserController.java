package com.example.be_medicalbooking.controller;

import com.example.be_medicalbooking.dto.reponse.ResponseMessage;
import com.example.be_medicalbooking.dto.requset.ChangePassword;
import com.example.be_medicalbooking.dto.requset.ProfileRequest;
import com.example.be_medicalbooking.model.user.User;
import com.example.be_medicalbooking.security.userprincipal.UserPrinciple;
import com.example.be_medicalbooking.service.user.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class UserController{
    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("")
    public ResponseEntity<?> getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((UserPrinciple) authentication.getPrincipal()).getId();
        User user= userService.findById(id).get();
        Profile profile= new Profile();
        BeanUtils.copyProperties(user,profile);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<?> upadateProfile (@Valid @RequestBody ProfileRequest profileRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((UserPrinciple) authentication.getPrincipal()).getId();
        User user= userService.findById(id).get();
        user.setName(profileRequest.getName());
        user.setGender(profileRequest.isGender());
        user.setBirthday(profileRequest.getBirthday());
        user.setIdCard(profileRequest.getIdCard());
        user.setPhone(profileRequest.getPhone());
        userService.save(user);
        return new ResponseEntity<>(new ResponseMessage("Success"), HttpStatus.OK);
    }
    @PutMapping("/updatePassword")
    public ResponseEntity<?> upadatePassword(@Valid @RequestBody ChangePassword changePassword){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((UserPrinciple) authentication.getPrincipal()).getId();
        User user= userService.findById(id).get();
        System.out.println(changePassword.getNewPassword());
        if (passwordEncoder.matches(changePassword.getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));

            userService.save(user);
            return new ResponseEntity<>(new ResponseMessage("Succcess"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseMessage("Fail"), HttpStatus.BAD_REQUEST);    }
}
