package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.dto.impl.CropDto;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.service.CropService;
import com.example.CropMonitoringSystem.util.AppUtil;
import com.example.CropMonitoringSystem.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/crops")
public class CropController {
    @Autowired
    private CropService cropService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(@RequestBody CropDto cropDto) {
        try {
            cropService.saveCrop(cropDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{cropId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadCropImage(@PathVariable("cropId") String cropId, @RequestParam("image") MultipartFile image) {
        try {
            if (image != null || cropId.matches(Regex.CROP_ID)) {
                byte[] imageBytes = image.getBytes();
                String cropImageBase64 = AppUtil.convertImageToBase64(imageBytes);
                cropService.uploadCropImage(cropId, cropImageBase64);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping(value = "/{cropId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCrop(@PathVariable("cropId") String cropId, @RequestBody CropDto cropDto) {
        try {
            cropService.updateCrop(cropId, cropDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{cropId}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("cropId") String cropId) {
        try {
            cropService.deleteCrop(cropId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{cropId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropDto getCrop(@RequestParam("cropId") String cropId) {
        return cropService.getSelectedCrop(cropId);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDto> getAllCrops() {
        return cropService.getAllCrops();
    }

}
