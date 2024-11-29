package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.EquipmentDao;
import com.example.CropMonitoringSystem.dto.impl.EquipmentDto;
import com.example.CropMonitoringSystem.entity.EquipmentType;
import com.example.CropMonitoringSystem.entity.Status;
import com.example.CropMonitoringSystem.entity.impl.EquipmentEntity;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.exception.NotFoundException;
import com.example.CropMonitoringSystem.service.EquipmentService;
import com.example.CropMonitoringSystem.service.FieldService;
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
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private StaffServiceImpl staffService;

    @Override
    public void saveEquipment(EquipmentDto equipmentDto) {
        equipmentDto.setEquipmentId(generateEquipmentId());
        if (equipmentDto.getStaffId() != null) {
            equipmentDto.setEquipmentStatus(Status.UNAVAILABLE);
        }
        EquipmentEntity save = equipmentDao.save(mapping.toEquipmentEntity(equipmentDto));
        if (save == null) {
            log.error("Equipment not saved");
            throw new DataPersistException("Equipment not saved");
        }
    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDto equipmentDto) {
        Optional<EquipmentEntity> seached = equipmentDao.findById(equipmentId);

        if (seached.isPresent()) {
            EquipmentEntity equipmentEntity = seached.get();
            equipmentEntity.setEquipmentName(equipmentDto.getEquipmentName());
            equipmentEntity.setEquipmentType(EquipmentType.valueOf(equipmentDto.getEquipmentType()));
            equipmentEntity.setEquipmentStatus(equipmentDto.getEquipmentStatus());
            if (equipmentDto.getStaffId() != null) {
                equipmentEntity.setStaff(mapping.toStaffEntity(staffService.getSelectedStaff(equipmentDto.getStaffId())));
                equipmentDto.setEquipmentStatus(Status.UNAVAILABLE);
            } else {
                equipmentEntity.setStaff(null);
                log.info("staff is null in updateEquipment id: " + equipmentId);
            }
            if (equipmentDto.getFieldId() != null) {
                equipmentEntity.setField(mapping.toFieldEntity(fieldService.getSelectedField(equipmentDto.getFieldId())));
            } else {
                equipmentEntity.setField(null);
                log.info("field is null in updateEquipment id: " + equipmentId);
            }
            equipmentDao.save(equipmentEntity);
        } else {
            log.error("Equipment +" + equipmentId + " not found");
            throw new NotFoundException("Equipment +" + equipmentId + " not found");
        }
    }

    @Override
    public void deleteEquipment(String equipmentId) {
        if (equipmentDao.existsById(equipmentId)) {
            equipmentDao.deleteById(equipmentId);
        } else {
            log.error("Equipment +" + equipmentId + " not found");
            throw new NotFoundException("Equipment +" + equipmentId + " not found");
        }
    }

    @Override
    public EquipmentDto getSelectedEquipment(String equipmentId) {
        Optional<EquipmentEntity> searched = equipmentDao.findById(equipmentId);
        if (searched.isPresent()) {
            return mapping.toEquipmentDto(searched.get());
        } else {
            log.error("Equipment +" + equipmentId + " not found");
            throw new NotFoundException("Equipment +" + equipmentId + " not found");
        }
    }

    @Override
    public List<EquipmentDto> getAllEquipment() {
        return mapping.toEquipmentDtoList(equipmentDao.findAll());
    }

    @Override
    public String generateEquipmentId() {
        String maxEquipmentId = equipmentDao.generateEquipmentId();

        if (maxEquipmentId == null) {
            return "E00-001";
        }

        int newEquipmentId = Integer.parseInt(maxEquipmentId.replace("E00-", "")) + 1;
        return String.format("E00-%03d", newEquipmentId);

    }
}
