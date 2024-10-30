package com.example.CropMonitoringSystem.service;

import com.example.CropMonitoringSystem.dto.impl.StaffDto;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDto staffDto);
    void updateStaff(String staffId, StaffDto staffDto);
    void deleteStaff(String staffId);
    StaffDto getSelectedStaff(String staffId);
    List<StaffDto> getAllStaffs();
    String generateStaffId();
}
