package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.UserDao;
import com.example.CropMonitoringSystem.dto.impl.UserDto;
import com.example.CropMonitoringSystem.entity.impl.UserEntity;
import com.example.CropMonitoringSystem.secure.JWTAuthResponse;
import com.example.CropMonitoringSystem.secure.SignIn;
import com.example.CropMonitoringSystem.service.AuthService;
import com.example.CropMonitoringSystem.service.JWTService;
import com.example.CropMonitoringSystem.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService {
    private final UserDao userDao;
    private final Mapping mapping;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var user = userDao.findById(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generatedToken = jwtService.generateToken(user);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JWTAuthResponse signUp(UserDto userDTO) {
        //Save user
        UserEntity savedUser = userDao.save(mapping.toUserEntity(userDTO));
        //Generate the token and return it
        var generatedToken = jwtService.generateToken(savedUser);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JWTAuthResponse refreshToken( String accessToken) {
        //extract username
        var username = jwtService.extractUsername(accessToken);
        //get user
        var user = userDao.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //generate new token
        var generatedToken = jwtService.refreshToken(user);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }
}
