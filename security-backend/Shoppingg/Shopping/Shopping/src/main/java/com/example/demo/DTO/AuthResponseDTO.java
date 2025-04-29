package com.example.demo.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class AuthResponseDTO {
    private String token;
    private String refreshToken;
    private String role;

}