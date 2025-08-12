// controller/AuthController.java
package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.LoginRequest;
import com.example.SpringSecurity.dto.LoginResponse;
import com.example.SpringSecurity.dto.RegisterRequest;
import com.example.SpringSecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
