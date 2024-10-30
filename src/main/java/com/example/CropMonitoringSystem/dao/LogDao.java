package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LogDao extends JpaRepository<LogEntity, String> {
    @Query("SELECT l.logId FROM LogEntity l ORDER BY l.logId DESC LIMIT 1")
    String generateLogId();
}
