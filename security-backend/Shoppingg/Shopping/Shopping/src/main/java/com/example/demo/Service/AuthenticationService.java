package com.example.demo.Service;

import com.example.demo.DTO.AuthResponseDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RefreshTokenRequest;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.Model.Role;
import com.example.demo.Model.UserEntity;
import com.example.demo.Service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    public UserEntity signup(RegisterDTO user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());
        userEntity.setRole(Role.valueOf(user.getRole()));
        return userService.save(userEntity);
    }
    public boolean existsByUsername(String username) {
        return userService.existsByUsername(username);
    }
    public AuthResponseDTO login(LoginDTO loginDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        var user = userService.findByUsername(loginDTO.getUsername());
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(jwt);
        authResponseDTO.setRefreshToken(refreshToken);
        authResponseDTO.setRole(user.getRole().toString());
        Date now = new Date();
        Date expDate = new Date(now.getTime() + 1000 * 60 * 10);
        return authResponseDTO;


    }

   public AuthResponseDTO refreshToken(RefreshTokenRequest refreshTokenRequest) {
        System.out.println("/n/n"+refreshTokenRequest.getToken()+"/n/n");
        String username = jwtService.extractUsername(refreshTokenRequest.getToken());
        UserEntity user = userService.findByUsername(username);
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
             var jwt = jwtService.generateToken(user);
            AuthResponseDTO authResponseDTO = new AuthResponseDTO();
            authResponseDTO.setToken(jwt);
            authResponseDTO.setRefreshToken(refreshTokenRequest.getToken());
            authResponseDTO.setRole(user.getRole().toString());
            return authResponseDTO;
        }
        return null;
   }

}
