package com.okejkadev.clinic_system.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddIntakeRequest {

    private LocalDate intakeDate;
    private Long clinicianId;
    private Long patientId;
    private Long medicationUnitId;
}
