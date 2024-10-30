package com.example.CropMonitoringSystem.entity.impl;

import com.example.CropMonitoringSystem.entity.EquipmentType;
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
@Table(name = "equipment")
public class EquipmentEntity implements SuperEntity {
    @Id
    private String equipmentId;
    private String equipmentName;

    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;

    @Enumerated(EnumType.STRING)
    private Status equipmentStatus;

    @ManyToOne
    @JoinColumn(name = "staffId")
    private StaffEntity staff;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    private FieldEntity field;
}
