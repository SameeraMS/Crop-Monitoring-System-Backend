package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.FieldDao;
import com.example.CropMonitoringSystem.dao.StaffDao;
import com.example.CropMonitoringSystem.dto.impl.StaffDto;
import com.example.CropMonitoringSystem.entity.impl.FieldEntity;
import com.example.CropMonitoringSystem.entity.impl.StaffEntity;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.exception.NotFoundException;
import com.example.CropMonitoringSystem.service.LogService;
import com.example.CropMonitoringSystem.service.StaffService;
import com.example.CropMonitoringSystem.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private LogService logService;
    @Autowired
    private FieldDao fieldDao;

    @Override
    public void saveStaff(StaffDto staffDto) {
        staffDto.setStaffId(generateStaffId());
        StaffEntity save = staffDao.save(mapping.toStaffEntity(staffDto));
        if (save == null) {
            throw new DataPersistException("Staff not saved");
        }
    }

    @Override
    public void updateStaff(String staffId, StaffDto staffDto) {
        Optional<StaffEntity> searched = staffDao.findById(staffId);

        if (searched.isPresent()) {
            StaffEntity staffEntity = searched.get();
            staffEntity.setBuildingNo(staffDto.getBuildingNo());
            staffEntity.setDesignation(staffDto.getDesignation());
            staffEntity.setCity(staffDto.getCity());
            staffEntity.setState(staffDto.getState());
            staffEntity.setPostalcode(staffDto.getPostalcode());
            staffEntity.setContactNo(staffDto.getContactNo());
            staffEntity.setEmail(staffDto.getEmail());
            staffEntity.setRole(staffDto.getRole());
            staffEntity.setFirstName(staffDto.getFirstName());
            staffEntity.setLastName(staffDto.getLastName());
            staffEntity.setGender(staffDto.getGender());
            staffEntity.setJoinedDate(staffDto.getJoinedDate());
            staffEntity.setLane(staffDto.getLane());
            staffEntity.setDob(staffDto.getDob());
            if (staffDto.getLogId() != null) {
                staffEntity.setLog(mapping.toLogEntity(logService.getSelectedLog(staffDto.getLogId())));
            } else {
                staffEntity.setLog(null);
            }

            staffDao.save(staffEntity);
        } else {
            throw new NotFoundException("Staff +" + staffId + " not found");
        }
    }

    @Override
    public void deleteStaff(String staffId) {
        if (staffDao.existsById(staffId)) {
            staffDao.deleteById(staffId);
        } else {
            throw new NotFoundException("Staff +" + staffId + " not found");
        }
    }

    @Override
    public StaffDto getSelectedStaff(String staffId) {
        Optional<StaffEntity> searched = staffDao.findById(staffId);
        if (searched.isPresent()) {
            return mapping.toStaffDto(searched.get());
        } else {
            throw new NotFoundException("Staff +" + staffId + " not found");
        }
    }

    @Override
    public List<StaffDto> getAllStaffs() {
        return mapping.toStaffDtoList(staffDao.findAll());
    }

    @Override
    public String generateStaffId() {
        String staffId = staffDao.generateStaffId();

        if (staffId == null) {
            return "S00-001";
        }

        int newStaffId = Integer.parseInt(staffId.replace("S00-", "")) + 1;
        return String.format("S00-%03d", newStaffId);
    }

    @Override
    public void assignFieldToStaff(String staffId, String fieldCode) {
        StaffEntity fetchedStaff = staffDao.findById(staffId).orElseThrow(() -> new NotFoundException("Staff id not found"));
        FieldEntity fetchedField = fieldDao.findById(fieldCode).orElseThrow(() -> new NotFoundException("Field id not found"));
        fetchedStaff.addField(fetchedField);
        staffDao.save(fetchedStaff);
    }

    @Override
    public void removeFieldFromStaff(String staffId, String fieldCode) {
        StaffEntity fetchedStaff = staffDao.findById(staffId).orElseThrow(() -> new NotFoundException("Staff id not found"));
        FieldEntity fetchedField = fieldDao.findById(fieldCode).orElseThrow(() -> new NotFoundException("Field id not found"));
        fetchedStaff.removeField(fetchedField);
        staffDao.save(fetchedStaff);
    }


}
