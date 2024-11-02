package com.example.CropMonitoringSystem.dto.impl;

import com.example.CropMonitoringSystem.dto.SuperDto;
import com.example.CropMonitoringSystem.entity.Gender;
import com.example.CropMonitoringSystem.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDto implements SuperDto {
    private String staffId;
    private String firstName;
    private String lastName;
    private String designation;
    private Gender gender;
    private Date joinedDate;
    private Date dob;
    private String buildingNo;
    private String lane;
    private String city;
    private String state;
    private String postalcode;
    private String contactNo;
    private String email;
    private Role role;
    private List<FieldDto> fields;
    private List<VehicleDto> vehicles;
    private List<EquipmentDto> equipments;
    private String logId;
}
