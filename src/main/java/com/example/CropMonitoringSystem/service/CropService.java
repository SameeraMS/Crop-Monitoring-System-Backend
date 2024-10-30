package com.example.CropMonitoringSystem.service;

import com.example.CropMonitoringSystem.dto.impl.CropDto;

import java.util.List;

public interface CropService {
    void saveCrop(CropDto cropDto);
    void updateCrop(String cropId,CropDto cropDto);
    void deleteCrop(String cropId);
    CropDto getSelectedCrop(String cropId);
    List<CropDto> getAllCrops();
    String generateCropId();
    void uploadCropImage(String cropId, String image);

}
