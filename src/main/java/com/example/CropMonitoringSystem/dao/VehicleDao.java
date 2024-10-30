package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleDao extends JpaRepository<VehicleEntity, String> {
    @Query("SELECT v.vehicleId FROM VehicleEntity v ORDER BY v.vehicleId DESC LIMIT 1")
    String generateVehicleId();
}
