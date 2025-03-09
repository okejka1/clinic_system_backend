package com.okejkadev.clinic_system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class IntakeDTO {
    private Long id;
    private LocalDate intakeDate;
    private UserShortDTO clinician;
    private PatientShortDTO patient;
    private MedicationUnitDTO medicationUnit;
}
