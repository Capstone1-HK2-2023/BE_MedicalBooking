package com.example.be_medicalbooking.controller;

import com.example.be_medicalbooking.dto.reponse.JwtResponse;
import com.example.be_medicalbooking.dto.reponse.ResponseMessage;
import com.example.be_medicalbooking.dto.requset.SignInForm;
import com.example.be_medicalbooking.dto.requset.SignUpForm;
import com.example.be_medicalbooking.model.user.AuthProvider;
import com.example.be_medicalbooking.model.user.ERole;
import com.example.be_medicalbooking.model.user.Role;
import com.example.be_medicalbooking.model.user.User;
import com.example.be_medicalbooking.security.jwt.JwtProvider;
import com.example.be_medicalbooking.security.userprincipal.UserPrinciple;
import com.example.be_medicalbooking.service.role.IRoleService;
import com.example.be_medicalbooking.service.user.IUserService;
import com.example.be_medicalbooking.utils.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    OTPService otpService;
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.OK);
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.OK);
        }
        if(otpService.getOtp(signUpRequest.getEmail()) == signUpRequest.getOtp()) {
            // Creating user's account
            User user = new User();
            user.setName(signUpRequest.getName());
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setProvider(AuthProvider.local);
            Set<Role> roles = new HashSet<>();
            Role userRole = roleService.findByName(ERole.User)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            roles.add(userRole);
            user.setRoles(roles);
            userService.createUser(user);
            otpService.clearOTP(String.valueOf(signUpRequest.getOtp()));
//            System.out.println("user"+user.toString());
            return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);

        }
        return new ResponseEntity<>(new ResponseMessage("Otp invalid !!!"), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),
                userDetails.getId() , userDetails.getName(), userDetails.getEmail() ,
                userDetails.getAuthorities()
        ));
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> confirmOtp(@Valid @RequestBody SignUpForm signUpForm){
        if(otpService.getOtp(signUpForm.getEmail()) == signUpForm.getOtp()) {
            return new ResponseEntity<>(signUpForm, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseMessage("Otp invalid !!!"), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/forgotPassword/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody SignUpForm signUpForm){
        if(otpService.getOtp(signUpForm.getEmail()) == signUpForm.getOtp()) {
            User user= userService.findByEmail(signUpForm.getEmail()).get();
            user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
            userService.save(user);
            otpService.clearOTP(String.valueOf(signUpForm.getOtp()));
            return new ResponseEntity<>(new ResponseMessage("Success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseMessage("Otp invalid !!!"), HttpStatus.BAD_REQUEST);
    }
}