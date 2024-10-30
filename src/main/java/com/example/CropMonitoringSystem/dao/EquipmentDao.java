package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EquipmentDao extends JpaRepository<EquipmentEntity, String> {
    @Query("SELECT e.equipmentId FROM EquipmentEntity e ORDER BY e.equipmentId DESC")
    String generateEquipmentId();
}
