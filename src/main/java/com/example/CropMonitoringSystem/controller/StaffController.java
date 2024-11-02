package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.dto.impl.StaffDto;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.exception.NotFoundException;
import com.example.CropMonitoringSystem.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staffs")
@CrossOrigin
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDto staffDto) {
        try {
            staffService.saveStaff(staffDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{staffId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Void> updateStaff(@PathVariable("staffId") String staffId, @RequestBody StaffDto staffDto) {
        try {
            staffService.updateStaff(staffId, staffDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{staffId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Void> deleteStaff(@PathVariable("staffId") String staffId) {
        try {
            staffService.deleteStaff(staffId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffDto getSelectedStaff(@PathVariable("staffId") String staffId) {
        return staffService.getSelectedStaff(staffId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDto> getAllStaffs() {
        return staffService.getAllStaffs();
    }

}
