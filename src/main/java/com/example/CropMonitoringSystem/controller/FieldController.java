package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.dto.impl.FieldDto;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.service.FieldService;
import com.example.CropMonitoringSystem.util.AppUtil;
import com.example.CropMonitoringSystem.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/fields")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveField(@RequestBody FieldDto fieldDto) {
        try {
            fieldService.saveField(fieldDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{fieldId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFieldImage(
            @PathVariable("fieldId") String fieldId,
            @RequestParam("image1") MultipartFile image1,
            @RequestParam("image2") MultipartFile image2
    ) {
        try {
            if (fieldId.matches(Regex.FIELD_ID)) {
                byte[] image1Bytes = image1.getBytes();
                String image1Base64 = AppUtil.convertImageToBase64(image1Bytes);
                byte[] image2Bytes = image2.getBytes();
                String image2Base64 = AppUtil.convertImageToBase64(image2Bytes);
                fieldService.uploadFieldImage(fieldId, image1Base64, image2Base64);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{fieldId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateField(@PathVariable("fieldId") String fieldId, FieldDto fieldDto) {
        try {
            fieldService.updateField(fieldId, fieldDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{fieldId}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldId") String fieldId) {
        try {
            fieldService.deleteField(fieldId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDto> getAllFields() {
        return fieldService.getAllFields();
    }

    @GetMapping(value = "/{fieldId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldDto getFieldById(@PathVariable("fieldId") String fieldId) {
        return fieldService.getSelectedField(fieldId);
    }
}
