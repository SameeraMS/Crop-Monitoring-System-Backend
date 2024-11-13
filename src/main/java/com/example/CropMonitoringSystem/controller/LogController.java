package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.dto.impl.LogDto;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.service.LogService;
import com.example.CropMonitoringSystem.util.AppUtil;
import com.example.CropMonitoringSystem.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/logs")
@CrossOrigin(origins = "*")
public class LogController {
    @Autowired
    private LogService logService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Map<String, String>> saveLog(@RequestBody LogDto logDto) {
        try {
            String saveId = logService.generateLogId();
            logService.saveLog(logDto);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("logId", saveId);

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
    public ResponseEntity<Void> uploadImage(
            @RequestPart("logId") String logId,
            @RequestPart("image") String image
    ) {
        if (logId.matches(Regex.LOG_ID)) {
            byte[] observedImageBytes = image.getBytes();
            String convertedImageToBase64 = AppUtil.convertImageToBase64(observedImageBytes);
            logService.uploadImage(logId, convertedImageToBase64);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{logId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Void> updateLog(@PathVariable("logId") String logId,@RequestBody LogDto logDto) {
        try {
            logService.updateLog(logId, logDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{logId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Void> deleteLog(@PathVariable("logId") String logId) {
        try {
            logService.deleteLog(logId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public List<LogDto> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping(value = "/{logId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public LogDto getSelectedLog(@PathVariable("logId") String logId) {
        return logService.getSelectedLog(logId);
    }
}
