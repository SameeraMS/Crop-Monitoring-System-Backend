package com.example.CropMonitoringSystem.dto.impl;

import com.example.CropMonitoringSystem.dto.SuperDto;
import com.example.CropMonitoringSystem.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto implements SuperDto {
    private String email;
    private String password;
    private Role role;
    private String roleCode;
}
