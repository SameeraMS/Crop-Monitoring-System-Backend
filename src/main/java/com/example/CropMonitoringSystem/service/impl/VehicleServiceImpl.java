package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.StaffDao;
import com.example.CropMonitoringSystem.dao.VehicleDao;
import com.example.CropMonitoringSystem.dto.impl.VehicleDto;
import com.example.CropMonitoringSystem.entity.impl.VehicleEntity;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.service.VehicleService;
import com.example.CropMonitoringSystem.util.Mapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveVehicle(VehicleDto vehicleDto) {
        vehicleDto.setVehicleId(generateVehicleId());
        VehicleEntity save = vehicleDao.save(mapping.toVehicleEntity(vehicleDto));
        if (save == null) {
            log.error("Vehicle not saved");
            throw new DataPersistException("Vehicle not saved");
        }
    }

    @Override
    public void updateVehicle(String vehicleId, VehicleDto vehicleDto) {
        Optional<VehicleEntity> searched = vehicleDao.findById(vehicleId);

        if (searched.isPresent()) {
            VehicleEntity vehicleEntity = searched.get();
            if (vehicleDto.getStaffId() != null) {
                vehicleEntity.setStaff(staffDao.getReferenceById(vehicleDto.getStaffId()));
            } else {
                vehicleEntity.setStaff(null);
            }
            vehicleDao.save(vehicleEntity);
        } else {
            log.error("Vehicle +" + vehicleId + " not found");
            throw new DataPersistException("Vehicle +" + vehicleId + " not found");
        }
    }

    @Override
    public void deleteVehicle(String vehicleId) {
        if (vehicleDao.existsById(vehicleId)) {
            vehicleDao.deleteById(vehicleId);
        } else {
            log.error("Vehicle +" + vehicleId + " not found");
            throw new DataPersistException("Vehicle +" + vehicleId + " not found");
        }
    }

    @Override
    public VehicleDto getSelectedVehicle(String vehicleId) {
        Optional<VehicleEntity> searched = vehicleDao.findById(vehicleId);
        if (searched.isPresent()) {
            return mapping.toVehicleDto(searched.get());
        } else {
            log.error("Vehicle +" + vehicleId + " not found");
            throw new DataPersistException("Vehicle +" + vehicleId + " not found");
        }
    }

    @Override
    public List<VehicleDto> getVehicles() {
        return mapping.toVehicleDtoList(vehicleDao.findAll());
    }

    @Override
    public String generateVehicleId() {
        String maxVehicleId = vehicleDao.generateVehicleId();

        if (maxVehicleId == null) {
            return "V00-001";
        }

        int newVehicleId = Integer.parseInt(maxVehicleId.replace("V00-", "")) + 1;
        return String.format("V00-%03d", newVehicleId);

    }


}
