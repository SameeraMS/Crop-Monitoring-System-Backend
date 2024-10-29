package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, String> {
}
