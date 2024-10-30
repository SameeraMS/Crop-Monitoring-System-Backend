package com.example.CropMonitoringSystem.dao;

import com.example.CropMonitoringSystem.entity.impl.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffDao extends JpaRepository<StaffEntity, String> {
    @Query("SELECT s.staffId FROM StaffEntity s ORDER BY s.staffId DESC LIMIT 1")
    String generateStaffId();
}
