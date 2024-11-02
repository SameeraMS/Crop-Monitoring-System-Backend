package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.dto.impl.VehicleDto;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.exception.NotFoundException;
import com.example.CropMonitoringSystem.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicles")
@CrossOrigin
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Void> saveVehicle(@RequestBody VehicleDto vehicleDto) {
        try {
            vehicleService.saveVehicle(vehicleDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{vehicleId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Void> updateVehicle(@PathVariable("vehicleId") String vehicleId, @RequestBody VehicleDto vehicleDto) {
        try {
            vehicleDto.setVehicleId(vehicleId);
            vehicleService.updateVehicle(vehicleId, vehicleDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{vehicleId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleId") String vehicleId) {
        try {
            vehicleService.deleteVehicle(vehicleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleDto getSelectedVehicle(@PathVariable("vehicleId") String vehicleId) {
        return vehicleService.getSelectedVehicle(vehicleId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDto> getAllVehicles() {
        return vehicleService.getVehicles();
    }

}