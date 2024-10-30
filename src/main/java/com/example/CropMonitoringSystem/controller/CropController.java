package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/crops")
public class CropController {
    @Autowired
    private CropService cropService;


}
