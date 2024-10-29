package com.example.CropMonitoringSystem.service;

import com.example.CropMonitoringSystem.dto.impl.FieldDto;

import java.util.List;

public interface FieldService {
    void saveField(FieldDto fieldDto);
    void updateField(String fieldId, FieldDto fieldDto);
    void deleteField(String fieldId);
    FieldDto getSelectedField(String fieldId);
    List<FieldDto> getAllFields();
}
