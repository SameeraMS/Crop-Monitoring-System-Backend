package com.example.CropMonitoringSystem.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")
@CrossOrigin
public class HealthController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String heathTest(){
        return "Crop Monitoring System is Working";
    }
}
