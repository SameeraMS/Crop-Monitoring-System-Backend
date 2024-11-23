package com.example.CropMonitoringSystem.dto.impl;

import com.example.CropMonitoringSystem.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDto implements SuperDto {
    private String cropId;
    private String commonName;
    private String scientificName;
    private String cropImg;
    private String category;
    private String cropSeason;
    private String fieldId;
    private String logId;
}
