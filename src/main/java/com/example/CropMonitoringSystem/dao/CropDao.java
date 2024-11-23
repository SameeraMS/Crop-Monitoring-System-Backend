package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CropDao extends JpaRepository<CropEntity, String> {
    @Query("SELECT c.cropId FROM CropEntity c ORDER BY c.cropId DESC LIMIT 1")
    String generateCropId();
}
