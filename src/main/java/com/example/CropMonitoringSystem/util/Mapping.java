package com.example.CropMonitoringSystem.util;

import com.example.CropMonitoringSystem.dto.impl.*;
import com.example.CropMonitoringSystem.entity.impl.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    //for crop
    public CropDto toCropDto(CropEntity cropEntity){
        return modelMapper.map(cropEntity, CropDto.class);
    }

    public CropEntity toCropEntity(CropDto cropDto){
        return modelMapper.map(cropDto, CropEntity.class);
    }

    public List<CropDto> toCropDtoList(List<CropEntity> cropEntities){
        return cropEntities.stream().map(this::toCropDto).toList();
    }

    //for field
    public FieldDto toFieldDto(FieldEntity fieldEntity){
        return modelMapper.map(fieldEntity, FieldDto.class);
    }

    public FieldEntity toFieldEntity(FieldDto fieldDto){
        return modelMapper.map(fieldDto, FieldEntity.class);
    }

    public List<FieldDto> toFieldDtoList(List<FieldEntity> fieldEntities){
        return fieldEntities.stream().map(this::toFieldDto).toList();
    }

    //for equipment
    public EquipmentDto toEquipmentDto(EquipmentEntity equipmentEntity){
        return modelMapper.map(equipmentEntity, EquipmentDto.class);
    }

    public EquipmentEntity toEquipmentEntity(EquipmentDto equipmentDto){
        return modelMapper.map(equipmentDto, EquipmentEntity.class);
    }

    public List<EquipmentDto> toEquipmentDtoList(List<EquipmentEntity> equipmentEntities){
        return equipmentEntities.stream().map(this::toEquipmentDto).toList();
    }

    //for staff
    public StaffDto toStaffDto(StaffEntity staffEntity){
        return modelMapper.map(staffEntity, StaffDto.class);
    }

    public StaffEntity toStaffEntity(StaffDto staffDto){
        return modelMapper.map(staffDto, StaffEntity.class);
    }

    public List<StaffDto> toStaffDtoList(List<StaffEntity> staffEntities){
        return staffEntities.stream().map(this::toStaffDto).toList();
    }

    //for log
    public LogDto toLogDto(LogEntity logEntity){
        return modelMapper.map(logEntity, LogDto.class);
    }

    public LogEntity toLogEntity(LogDto logDto){
        return modelMapper.map(logDto, LogEntity.class);
    }

    public List<LogDto> toLogDtoList(List<LogEntity> logEntities){
        return logEntities.stream().map(this::toLogDto).toList();
    }

    //for user
    public UserDto toUserDto(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserEntity toUserEntity(UserDto userDto){
        return modelMapper.map(userDto, UserEntity.class);
    }

    public List<UserDto> toUserDtoList(List<UserEntity> userEntities){
        return userEntities.stream().map(this::toUserDto).toList();
    }

    //for vehicle
    public VehicleDto toVehicleDto(VehicleEntity vehicleEntity){
        return modelMapper.map(vehicleEntity, VehicleDto.class);
    }

    public VehicleEntity toVehicleEntity(VehicleDto vehicleDto){
        return modelMapper.map(vehicleDto, VehicleEntity.class);
    }

    public List<VehicleDto> toVehicleDtoList(List<VehicleEntity> vehicleEntities){
        return vehicleEntities.stream().map(this::toVehicleDto).toList();
    }
}
