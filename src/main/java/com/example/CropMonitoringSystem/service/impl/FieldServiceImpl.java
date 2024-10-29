package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.FieldDao;
import com.example.CropMonitoringSystem.dto.impl.FieldDto;
import com.example.CropMonitoringSystem.entity.impl.FieldEntity;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.exception.NotFoundException;
import com.example.CropMonitoringSystem.service.FieldService;
import com.example.CropMonitoringSystem.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveField(FieldDto fieldDto) {
        FieldEntity save = fieldDao.save(mapping.toFieldEntity(fieldDto));
        if (save == null) {
            throw new DataPersistException("Field not saved");
        }
    }

    @Override
    public void updateField(String fieldId, FieldDto fieldDto) {
        Optional<FieldEntity> searched = fieldDao.findById(fieldId);
        if (searched.isPresent()) {
            fieldDao.save(mapping.toFieldEntity(fieldDto));
        } else {
            throw new NotFoundException("Field +" + fieldId + " not found");
        }
    }

    @Override
    public void deleteField(String fieldId) {
        if (fieldDao.existsById(fieldId)) {
            fieldDao.deleteById(fieldId);
        } else {
            throw new NotFoundException("Field +" + fieldId + " not found");
        }
    }

    @Override
    public FieldDto getSelectedField(String fieldId) {
        Optional<FieldEntity> searched = fieldDao.findById(fieldId);
        if (searched.isPresent()) {
            return mapping.toFieldDto(searched.get());
        } else {
            throw new NotFoundException("Field +" + fieldId + " not found");
        }
    }

    @Override
    public List<FieldDto> getAllFields() {
        return mapping.toFieldDtoList(fieldDao.findAll());
    }
}
