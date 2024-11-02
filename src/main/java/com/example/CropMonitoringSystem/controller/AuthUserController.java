package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.dto.impl.UserDto;
import com.example.CropMonitoringSystem.entity.Role;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.secure.JWTAuthResponse;
import com.example.CropMonitoringSystem.secure.SignIn;
import com.example.CropMonitoringSystem.service.UserService;
import com.example.CropMonitoringSystem.service.impl.AuthServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/auth/")
@RestController
@RequiredArgsConstructor
public class AuthUserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceIMPL authService;

    @Value("${administrator.code}")
    private String adminCode;
    @Value("${manager.code}")
    private String managerCode;
    @Value("${scientist.code}")
    private String scientistCode;

    @PostMapping(value = "signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> saveUser(@RequestBody UserDto userDto) {

        if (userDto.getRole().equals(Role.ADMINISTRATIVE)) {
            if (!userDto.getRoleCode().equals(adminCode)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }

        if (userDto.getRole().equals(Role.MANAGER)) {
            if (!userDto.getRoleCode().equals(managerCode)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }

        if (userDto.getRole().equals(Role.SCIENTIST)) {
            if (!userDto.getRoleCode().equals(scientistCode)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }

        try {

            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userService.saveUser(userDto);
            return ResponseEntity.ok(authService.signUp(userDto));
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn) {
        return ResponseEntity.ok(authService.signIn(signIn));
    }

    @PostMapping("refresh")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestParam ("refreshToken") String refreshToken) {
        return null;
    }
}