package com.example.CropMonitoringSystem.service;

import com.example.CropMonitoringSystem.dto.impl.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void updateUser(String userId, UserDto userDto);
    void deleteUser(String userId);
    UserDto getSelectedUser(String userId);
    List<UserDto> getAllUsers();

    UserDetailsService userDetailsService();

}
