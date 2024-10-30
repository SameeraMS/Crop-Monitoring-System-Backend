package com.example.CropMonitoringSystem.service;

import com.example.CropMonitoringSystem.dto.impl.VehicleDto;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDto vehicleDto);
    void updateVehicle(String vehicleId, VehicleDto vehicleDto);
    void deleteVehicle(String vehicleId);
    VehicleDto getSelectedVehicle(String vehicleId);
    List<VehicleDto> getVehicles();
    String generateVehicleId();
}
