package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.dto.impl.FieldDto;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.service.FieldService;
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
@RequestMapping("api/v1/fields")
@CrossOrigin
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Map<String, String>> saveField(@RequestBody FieldDto fieldDto) {
        try {
            String fieldId = fieldService.generateFieldId();
            fieldService.saveField(fieldDto);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("fieldId", fieldId);
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
    public ResponseEntity<Void> uploadFieldImage(
            @RequestPart("fieldId") String fieldId,
            @RequestPart("image1") String image1,
            @RequestPart("image2") String image2
    ) {
        try {
            if (fieldId.matches(Regex.FIELD_ID)) {
                fieldService.uploadFieldImage(fieldId, image1, image2);
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
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Void> updateField(@PathVariable("fieldId") String fieldId,@RequestBody FieldDto fieldDto) {
        try {
            if (!fieldId.matches(Regex.FIELD_ID)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                fieldService.updateField(fieldId, fieldDto);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{fieldId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldId") String fieldId) {
        try {
            if (!fieldId.matches(Regex.FIELD_ID)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                fieldService.deleteField(fieldId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
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
        if (!fieldId.matches(Regex.FIELD_ID)) {
            return null;
        }
        return fieldService.getSelectedField(fieldId);
    }
}
