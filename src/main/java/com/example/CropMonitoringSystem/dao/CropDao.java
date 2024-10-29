package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropDao extends JpaRepository<CropEntity, String> {
}
