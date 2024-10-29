package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.VehicleDao;
import com.example.CropMonitoringSystem.dto.impl.VehicleDto;
import com.example.CropMonitoringSystem.entity.impl.VehicleEntity;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.service.VehicleService;
import com.example.CropMonitoringSystem.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveVehicle(VehicleDto vehicleDto) {
        VehicleEntity save = vehicleDao.save(mapping.toVehicleEntity(vehicleDto));
        if (save == null) {
            throw new DataPersistException("Vehicle not saved");
        }
    }

    @Override
    public void updateVehicle(String vehicleId, VehicleDto vehicleDto) {
        Optional<VehicleEntity> searched = vehicleDao.findById(vehicleId);

        if (searched.isPresent()) {
            vehicleDao.save(mapping.toVehicleEntity(vehicleDto));
        } else {
            throw new DataPersistException("Vehicle +" + vehicleId + " not found");
        }
    }

    @Override
    public void deleteVehicle(String vehicleId) {
        if (vehicleDao.existsById(vehicleId)) {
            vehicleDao.deleteById(vehicleId);
        } else {
            throw new DataPersistException("Vehicle +" + vehicleId + " not found");
        }
    }

    @Override
    public VehicleDto getSelectedVehicle(String vehicleId) {
        Optional<VehicleEntity> searched = vehicleDao.findById(vehicleId);
        if (searched.isPresent()) {
            return mapping.toVehicleDto(searched.get());
        } else {
            throw new DataPersistException("Vehicle +" + vehicleId + " not found");
        }
    }

    @Override
    public List<VehicleDto> getVehicles() {
        return mapping.toVehicleDtoList(vehicleDao.findAll());
    }


}
