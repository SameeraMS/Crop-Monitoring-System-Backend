package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentDao extends JpaRepository<EquipmentEntity, String> {
}
