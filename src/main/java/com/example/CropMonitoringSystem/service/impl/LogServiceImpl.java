package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.LogDao;
import com.example.CropMonitoringSystem.dto.impl.LogDto;
import com.example.CropMonitoringSystem.entity.impl.LogEntity;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.service.LogService;
import com.example.CropMonitoringSystem.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveLog(LogDto logDto) {
        LogEntity save = logDao.save(mapping.toLogEntity(logDto));
        if (save == null) {
            throw new DataPersistException("Log not saved");
        }
    }

    @Override
    public void updateLog(String logId, LogDto logDto) {
        Optional<LogEntity> searched = logDao.findById(logId);
        if (searched.isPresent()) {
            logDao.save(mapping.toLogEntity(logDto));
        } else {
            throw new DataPersistException("Log +" + logId + " not found");
        }
    }

    @Override
    public void deleteLog(String logId) {
        if (logDao.existsById(logId)) {
            logDao.deleteById(logId);
        } else {
            throw new DataPersistException("Log +" + logId + " not found");
        }
    }

    @Override
    public LogDto getSelectedLog(String logId) {
        Optional<LogEntity> searched = logDao.findById(logId);
        if (searched.isPresent()) {
            return mapping.toLogDto(searched.get());
        } else {
            throw new DataPersistException("Log +" + logId + " not found");
        }
    }

    @Override
    public List<LogDto> getAllLogs() {
        return mapping.toLogDtoList(logDao.findAll());
    }
}
