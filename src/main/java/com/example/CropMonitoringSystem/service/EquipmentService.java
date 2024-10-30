package com.example.CropMonitoringSystem.service;

import com.example.CropMonitoringSystem.dto.impl.EquipmentDto;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDto equipmentDto);
    void updateEquipment(String equipmentId, EquipmentDto equipmentDto);
    void deleteEquipment(String equipmentId);
    EquipmentDto getSelectedEquipment(String equipmentId);
    List<EquipmentDto> getAllEquipment();
    String generateEquipmentId();
}
