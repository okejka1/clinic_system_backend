package com.okejkadev.clinic_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "intakes")
public class Intake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Intake date is required")
    private LocalDate intakeDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User clinician;

    @OneToOne
    @JoinColumn(name = "medication_unit_id")
    private MedicationUnit medicationUnit;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

}
