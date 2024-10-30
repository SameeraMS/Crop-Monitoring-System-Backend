package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FieldDao extends JpaRepository<FieldEntity, String> {
    @Query("SELECT f.fieldId FROM FieldEntity f ORDER BY f.fieldId DESC LIMIT 1")
    String generateFieldId();
}
