package com.example.CropMonitoringSystem.dto.impl;

import com.example.CropMonitoringSystem.dto.status.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDto implements FieldStatus {
    private String fieldId;
    private String fieldName;
    private String fieldLocation;
    private String fieldSize;
    private List<CropDto> crops;
    private List<StaffDto> staffs;
    private String image1;
    private String image2;
    private List<EquipmentDto> equipments;
    private String logId;
}
