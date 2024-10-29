package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleDao extends JpaRepository<VehicleEntity, String> {
}
