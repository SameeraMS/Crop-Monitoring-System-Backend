package com.example.CropMonitoringSystem.dto.impl;

import com.example.CropMonitoringSystem.dto.SuperDto;
import com.example.CropMonitoringSystem.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDto implements SuperDto {
    private String vehicleId;
    private String licenNo;
    private String category;
    private String fuelType;
    private Status vehicleStatus;
    private String staffId;
    private String remark;
}
