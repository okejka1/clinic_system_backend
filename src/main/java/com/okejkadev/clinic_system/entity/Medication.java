package com.okejkadev.clinic_system.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "medications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "dosage", "company"})
})
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Dosage is required")
    private String dosage;

    @Column(nullable = false)
    @NotBlank(message = "Company is required")
    private String company;

    private String description;
    private String medicationPhotoUrl;

    private boolean isActive = true;

    private int criticalUnitThreshold;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    List<MedicationUnit> medicationUnits = new ArrayList<>();


}
