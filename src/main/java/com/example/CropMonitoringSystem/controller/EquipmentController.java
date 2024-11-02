package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.dto.impl.EquipmentDto;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/equipments")
@CrossOrigin
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDto equipmentDto) {
        try {
            equipmentService.saveEquipment(equipmentDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{equipmentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Void> updateEquipment(@PathVariable("equipmentId") String equipmentId, @RequestBody EquipmentDto equipmentDto) {
        try {
            equipmentService.updateEquipment(equipmentId, equipmentDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{equipmentId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("equipmentId") String equipmentId) {
        try {
            equipmentService.deleteEquipment(equipmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{equipmentId}")
    public EquipmentDto getEquipment(@PathVariable("equipmentId") String equipmentId) {
        return equipmentService.getSelectedEquipment(equipmentId);
    }

    @GetMapping
    public Iterable<EquipmentDto> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }
}
