package com.example.CropMonitoringSystem.entity.impl;

import com.example.CropMonitoringSystem.entity.Status;
import com.example.CropMonitoringSystem.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity implements SuperEntity {
    @Id
    private String vehicleId;
    private String licenNo;
    private String category;
    private String fuelType;

    @Enumerated(EnumType.STRING)
    private Status vehicleStatus;

    @ManyToOne
    @JoinColumn(name = "staffId",nullable = false)
    private StaffEntity staff;

    private String remark;
}
