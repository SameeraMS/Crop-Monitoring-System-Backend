package com.example.CropMonitoringSystem.service;


import com.example.CropMonitoringSystem.dto.impl.UserDto;
import com.example.CropMonitoringSystem.secure.JWTAuthResponse;
import com.example.CropMonitoringSystem.secure.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(UserDto userDTO);
    JWTAuthResponse refreshToken(String accessToken);
}
