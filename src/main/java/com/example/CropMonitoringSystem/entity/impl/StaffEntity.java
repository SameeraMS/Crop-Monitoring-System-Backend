package com.example.CropMonitoringSystem.entity.impl;

import com.example.CropMonitoringSystem.entity.Gender;
import com.example.CropMonitoringSystem.entity.Role;
import com.example.CropMonitoringSystem.entity.SuperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "staff")
public class StaffEntity implements SuperEntity {
    @Id
    private String staffId;
    private String firstName;
    private String lastName;
    private String designation;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Date joinedDate;
    private Date dob;
    private String buildingNo;
    private String lane;
    private String city;
    private String state;
    private String postalcode;
    private String contactNo;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(
            name = "staff_field",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "field_id")
    )
    private List<FieldEntity> fields;

    @JsonIgnore
    @OneToMany(mappedBy = "staff",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<VehicleEntity> vehicles;

    @JsonIgnore
    @OneToMany(mappedBy = "staff",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EquipmentEntity> equipments;

    @ManyToOne
    @JoinColumn(name = "logId")
    private LogEntity log;

    public void addField(FieldEntity field){
        fields.add(field);
        field.getStaffs().add(this);
    }

    public void removeField(FieldEntity field){
        fields.remove(field);
        field.getStaffs().remove(this);
    }
}
