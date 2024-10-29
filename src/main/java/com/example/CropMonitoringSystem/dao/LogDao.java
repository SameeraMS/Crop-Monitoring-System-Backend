package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogDao extends JpaRepository<LogEntity, String> {
}
