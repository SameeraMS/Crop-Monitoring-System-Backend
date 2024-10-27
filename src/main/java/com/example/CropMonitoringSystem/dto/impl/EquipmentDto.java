package com.example.CropMonitoringSystem.dto.impl;

import com.example.CropMonitoringSystem.dto.status.EquipmentStatus;
import com.example.CropMonitoringSystem.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDto implements EquipmentStatus {
    private String equipmentId;
    private String equipmentName;
    private String equipmentType;
    private Status equipmentStatus;
    private String staffId;
    private String fieldId;
}
