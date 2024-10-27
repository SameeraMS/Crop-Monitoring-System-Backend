package com.example.CropMonitoringSystem.entity.impl;

import com.example.CropMonitoringSystem.entity.Role;
import com.example.CropMonitoringSystem.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class UserEntity implements SuperEntity {
    @Id
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
