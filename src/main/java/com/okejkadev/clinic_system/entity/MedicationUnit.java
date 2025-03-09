package com.okejkadev.clinic_system.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "medications_units")
public class MedicationUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Future(message = "Check out date must be in the future")
    private LocalDate expiryDate;

    @NotNull
    private String status;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @OneToOne
    @JoinColumn(name = "intake_id")
    private Intake intake;

}
