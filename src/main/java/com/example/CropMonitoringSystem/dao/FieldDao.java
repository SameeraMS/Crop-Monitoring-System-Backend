package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldDao extends JpaRepository<FieldEntity, String> {
}