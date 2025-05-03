package com.example.demo.DTO;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String email;
    private String password;
    private String role;
}
