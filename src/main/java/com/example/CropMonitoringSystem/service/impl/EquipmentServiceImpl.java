package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.EquipmentDao;
import com.example.CropMonitoringSystem.dto.impl.EquipmentDto;
import com.example.CropMonitoringSystem.entity.impl.EquipmentEntity;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.exception.NotFoundException;
import com.example.CropMonitoringSystem.service.EquipmentService;
import com.example.CropMonitoringSystem.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveEquipment(EquipmentDto equipmentDto) {
        EquipmentEntity save = equipmentDao.save(mapping.toEquipmentEntity(equipmentDto));
        if (save == null) {
            throw new DataPersistException("Equipment not saved");
        }
    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDto equipmentDto) {
        Optional<EquipmentEntity> seached = equipmentDao.findById(equipmentId);

        if (seached.isPresent()) {
            equipmentDao.save(mapping.toEquipmentEntity(equipmentDto));
        } else {
            throw new NotFoundException("Equipment +" + equipmentId + " not found");
        }
    }

    @Override
    public void deleteEquipment(String equipmentId) {
        if (equipmentDao.existsById(equipmentId)) {
            equipmentDao.deleteById(equipmentId);
        } else {
            throw new NotFoundException("Equipment +" + equipmentId + " not found");
        }
    }

    @Override
    public EquipmentDto getSelectedEquipment(String equipmentId) {
        Optional<EquipmentEntity> searched = equipmentDao.findById(equipmentId);
        if (searched.isPresent()) {
            return mapping.toEquipmentDto(searched.get());
        } else {
            throw new NotFoundException("Equipment +" + equipmentId + " not found");
        }
    }

    @Override
    public List<EquipmentDto> getAllEquipment() {
        return mapping.toEquipmentDtoList(equipmentDao.findAll());
    }
}
