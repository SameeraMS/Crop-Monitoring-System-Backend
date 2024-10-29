package com.example.CropMonitoringSystem.service;

import com.example.CropMonitoringSystem.dto.impl.LogDto;

import java.util.List;

public interface LogService {
    void saveLog(LogDto logDto);
    void updateLog(String logId,LogDto logDto);
    void deleteLog(String logId);
    LogDto getSelectedLog(String logId);
    List<LogDto> getAllLogs();
}
