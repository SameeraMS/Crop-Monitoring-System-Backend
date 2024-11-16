package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.CropDao;
import com.example.CropMonitoringSystem.dao.FieldDao;
import com.example.CropMonitoringSystem.dao.LogDao;
import com.example.CropMonitoringSystem.dto.impl.CropDto;
import com.example.CropMonitoringSystem.entity.impl.CropEntity;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.exception.NotFoundException;
import com.example.CropMonitoringSystem.service.CropService;
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
public class CropServiceImpl implements CropService {
    @Autowired
    private CropDao cropDao;
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private LogDao logDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveCrop(CropDto cropDto) {
        cropDto.setCropId(generateCropId());
        CropEntity save = cropDao.save(mapping.toCropEntity(cropDto));
        if (save == null) {
            log.error("Crop not saved");
            throw new DataPersistException("Crop not saved");
        }
    }

    @Override
    public void updateCrop(String cropId, CropDto cropDto) {
        Optional<CropEntity> searched = cropDao.findById(cropId);
        if (searched.isPresent()) {
            CropEntity cropEntity = searched.get();
            cropEntity.setCommonName(cropDto.getCommonName());
            cropEntity.setScientificName(cropDto.getScientificName());
            cropEntity.setCategory(cropDto.getCategory());
            cropEntity.setCropSeason(cropDto.getCropSeason());
            if (cropDto.getFieldId() != null) {
                cropEntity.setField(fieldDao.getReferenceById(cropDto.getFieldId()));
            } else {
                cropEntity.setField(null);
                log.info("Field is null for crop " + cropId);
            }

            cropDao.save(cropEntity);
        } else {
            log.error("Crop +" + cropId + " not found");
            throw new NotFoundException("Crop +" + cropId + " not found");
        }

    }

    @Override
    public void deleteCrop(String cropId) {
        if (cropDao.existsById(cropId)) {
            cropDao.deleteById(cropId);
        } else {
            log.error("Crop +" + cropId + " not found");
            throw new NotFoundException("Crop +" + cropId + " not found");
        }

    }

    @Override
    public CropDto getSelectedCrop(String cropId) {
        Optional<CropEntity> searched = cropDao.findById(cropId);
        if (searched.isPresent()) {
            return mapping.toCropDto(searched.get());
        } else {
            log.error("Crop +" + cropId + " not found");
            throw new NotFoundException("Crop +" + cropId + " not found");
        }
    }

    @Override
    public List<CropDto> getAllCrops() {
        return mapping.toCropDtoList(cropDao.findAll());
    }

    @Override
    public String generateCropId() {
        String maxCropId = cropDao.generateCropId();

        if (maxCropId == null) {
            return "C00-001";
        }

        int newCropId = Integer.parseInt(maxCropId.replace("C00-", "")) + 1;
        return String.format("C00-%03d", newCropId);

    }

    @Override
    public void uploadCropImage(String cropId, String image) {

        Optional<CropEntity> searched = cropDao.findById(cropId);
        if (searched.isPresent()) {
            searched.get().setCropImg(image);
            cropDao.save(searched.get());
        } else {
            log.error("Crop +" + cropId + " not found");
            throw new NotFoundException("Crop +" + cropId + " not found");
        }
    }
}
