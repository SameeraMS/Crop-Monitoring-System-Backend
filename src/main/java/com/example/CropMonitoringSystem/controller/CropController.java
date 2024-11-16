package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.dto.impl.CropDto;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.service.CropService;
import com.example.CropMonitoringSystem.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/crops")
@CrossOrigin
public class CropController {
    @Autowired
    private CropService cropService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Map<String, String>> saveCrop(@RequestBody CropDto cropDto) {
        try {
            String cropId = cropService.generateCropId();
            cropService.saveCrop(cropDto);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("cropId", cropId);

            return new ResponseEntity<>(responseBody,HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Void> uploadCropImage(
            @RequestPart("cropId") String cropId,
            @RequestPart("image") String image
    ) {
        if (image != null || cropId.matches(Regex.CROP_ID)) {
            cropService.uploadCropImage(cropId, image);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping(value = "/{cropId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
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
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
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
    public CropDto getCrop(@PathVariable("cropId") String cropId) {
        return cropService.getSelectedCrop(cropId);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDto> getAllCrops() {
        return cropService.getAllCrops();
    }

}
