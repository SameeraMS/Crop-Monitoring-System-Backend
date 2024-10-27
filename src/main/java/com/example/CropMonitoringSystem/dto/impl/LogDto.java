package com.example.CropMonitoringSystem.dto.impl;

import com.example.CropMonitoringSystem.dto.status.LogStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogDto implements LogStatus {
    private String logId;
    private String logDate;
    private String logDetails;
    private String image;
    private List<FieldDto> fields;
    private List<CropDto> crops;
    private List<StaffDto> staffs;
}
