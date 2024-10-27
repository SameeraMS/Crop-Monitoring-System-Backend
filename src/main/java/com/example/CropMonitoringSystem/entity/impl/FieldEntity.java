package com.example.CropMonitoringSystem.entity.impl;

import com.example.CropMonitoringSystem.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "field")
public class FieldEntity implements SuperEntity {
    @Id
    private String fieldId;
    private String fieldName;
    private Point fieldLocation;
    private Double fieldSize;

    @OneToMany(mappedBy = "field",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CropEntity> crops;

    @ManyToMany(mappedBy = "fields")
    private List<StaffEntity> staffs;

    @Column(columnDefinition = "LONGTEXT")
    private String image1;

    @Column(columnDefinition = "LONGTEXT")
    private String image2;

    @OneToMany(mappedBy = "field",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EquipmentEntity> equipments;

    @ManyToOne
    @JoinColumn(name = "logId",nullable = false)
    private LogEntity log;


}
