package com.example.CropMonitoringSystem.entity.impl;

import com.example.CropMonitoringSystem.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "log")
public class LogEntity implements SuperEntity {
    @Id
    private String logId;
    private Date logDate;
    private String logDetails;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @OneToMany(mappedBy = "log",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<FieldEntity> fields;

    @OneToMany(mappedBy = "log",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CropEntity> crops;

    @OneToMany(mappedBy = "log",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<StaffEntity> staffs;
}
