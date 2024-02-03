package com.example.platform.controller;

import com.example.platform.jwt.request.LoginRequest;
import com.example.platform.jwt.request.SignupRequest;
import com.example.platform.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateAccount(@RequestBody LoginRequest loginRequest) {

        return authService.signIn(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerAccount(@RequestBody SignupRequest signupRequest) {

        return authService.signUp(signupRequest);
    }
}
