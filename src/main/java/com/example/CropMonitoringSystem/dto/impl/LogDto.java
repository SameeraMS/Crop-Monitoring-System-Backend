package com.example.CropMonitoringSystem.dto.impl;

import com.example.CropMonitoringSystem.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogDto implements SuperDto {
    private String logId;
    private Date logDate;
    private String logDetails;
    private String image;
    private List<FieldDto> fields;
    private List<CropDto> crops;
    private List<StaffDto> staffs;
}
