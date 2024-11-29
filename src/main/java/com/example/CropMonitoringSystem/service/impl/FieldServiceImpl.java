package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.FieldDao;
import com.example.CropMonitoringSystem.dto.impl.FieldDto;
import com.example.CropMonitoringSystem.entity.impl.FieldEntity;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.exception.NotFoundException;
import com.example.CropMonitoringSystem.service.FieldService;
import com.example.CropMonitoringSystem.service.LogService;
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
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private LogService logService;

    @Override
    public void saveField(FieldDto fieldDto) {
        fieldDto.setFieldId(generateFieldId());
        FieldEntity save = fieldDao.save(mapping.toFieldEntity(fieldDto));
        if (save == null) {
            log.error("Field not saved");
            throw new DataPersistException("Field not saved");
        }
    }

    @Override
    public void updateField(String fieldId, FieldDto fieldDto) {
        Optional<FieldEntity> searched = fieldDao.findById(fieldId);
        if (searched.isPresent()) {
            FieldEntity fieldEntity = searched.get();
            fieldEntity.setFieldName(fieldDto.getFieldName());
            fieldEntity.setFieldLocation(fieldDto.getFieldLocation());
            fieldEntity.setFieldSize(Double.valueOf(fieldDto.getFieldSize()));
            if (fieldEntity.getLog() != null) {
                fieldEntity.setLog(mapping.toLogEntity(logService.getSelectedLog(fieldDto.getLogId())));
            } else {
                fieldEntity.setLog(null);
                log.info("Log is null in updateField id: " + fieldId);
            }
            fieldDao.save(fieldEntity);
        } else {
            log.error("Field not found for updateField id: " + fieldId);
            throw new NotFoundException("Field +" + fieldId + " not found");
        }
    }

    @Override
    public void deleteField(String fieldId) {
        if (fieldDao.existsById(fieldId)) {
            fieldDao.deleteById(fieldId);
        } else {
            log.error("Field not found for deleteField id: " + fieldId);
            throw new NotFoundException("Field +" + fieldId + " not found");
        }
    }

    @Override
    public FieldDto getSelectedField(String fieldId) {
        Optional<FieldEntity> searched = fieldDao.findById(fieldId);
        if (searched.isPresent()) {
            return mapping.toFieldDto(searched.get());
        } else {
            log.error("Field not found for getSelectedField id: " + fieldId);
            throw new NotFoundException("Field +" + fieldId + " not found");
        }
    }

    @Override
    public List<FieldDto> getAllFields() {
        return mapping.toFieldDtoList(fieldDao.findAll());
    }

    @Override
    public String generateFieldId() {
        String maxFieldId = fieldDao.generateFieldId();

        if (maxFieldId == null) {
            return "F00-001";
        }

        int newFieldId = Integer.parseInt(maxFieldId.replace("F00-", "")) + 1;
        return String.format("F00-%03d", newFieldId);

    }

    @Override
    public void uploadFieldImage(String fieldId, String image1, String image2) {
        Optional<FieldEntity> searched = fieldDao.findById(fieldId);
        if (searched.isPresent()) {
            if (image1 != null){
                searched.get().setImage1(image1);
            } else {
                searched.get().setImage1(searched.get().getImage1());
            }
            if (image2 != null){
                searched.get().setImage2(image2);
            } else {
                searched.get().setImage1(searched.get().getImage2());
            }

        } else {
            log.error("Field not found for uploadFieldImage id: " + fieldId);
            throw new NotFoundException("Field +" + fieldId + " not found");
        }
    }
}
