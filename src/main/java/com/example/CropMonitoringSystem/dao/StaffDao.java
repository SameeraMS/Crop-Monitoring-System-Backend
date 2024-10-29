package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffDao extends JpaRepository<StaffEntity, String> {
}
