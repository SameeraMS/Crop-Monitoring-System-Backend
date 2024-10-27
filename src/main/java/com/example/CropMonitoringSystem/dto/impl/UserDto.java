package com.example.CropMonitoringSystem.dto.impl;

import com.example.CropMonitoringSystem.dto.status.UserStatus;
import com.example.CropMonitoringSystem.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto implements UserStatus {
    private String email;
    private String password;
    private Role role;
}
