package com.okejkadev.clinic_system.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BulkMedicationUnitRequestDTO {
    private LocalDate expiryDate;
    private String status;
    private Integer quantity;
}

