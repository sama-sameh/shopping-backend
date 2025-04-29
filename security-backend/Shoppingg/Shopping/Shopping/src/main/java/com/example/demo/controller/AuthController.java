package com.example.demo.controller;

import com.example.demo.DTO.AuthResponseDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RefreshTokenRequest;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.Model.UserEntity;

import com.example.demo.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")

public class AuthController {
    private AuthenticationService authenticationService;
    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterDTO userDTO) {
        System.out.println(userDTO);
        return ResponseEntity.ok(authenticationService.signup(userDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO>login(@RequestBody LoginDTO credinals){
        System.out.println(credinals);
        var response = authenticationService.login(credinals);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO>refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }


}
