package com.example.CropMonitoringSystem.entity.impl;

import com.example.CropMonitoringSystem.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "crop")
public class CropEntity implements SuperEntity {
    @Id
    private String cropId;
    private String commonName;
    private String scientificName;

    @Column(columnDefinition = "LONGTEXT")
    private String cropImg;

    private String category;
    private String cropSeason;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    private FieldEntity field;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(
            name = "crop_log",
            joinColumns = @JoinColumn(name = "cropId"),
            inverseJoinColumns = @JoinColumn(name = "logId")
    )
    private List<LogEntity> logs;
}
