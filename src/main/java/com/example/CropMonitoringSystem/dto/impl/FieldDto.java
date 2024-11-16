package com.example.CropMonitoringSystem.dto.impl;

import com.example.CropMonitoringSystem.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDto implements SuperDto {
    private String fieldId;
    private String fieldName;
    private Point fieldLocation;
    private String fieldSize;
    private List<CropDto> crops;
    private String image1;
    private String image2;
    private List<EquipmentDto> equipments;
    private String logId;
}
