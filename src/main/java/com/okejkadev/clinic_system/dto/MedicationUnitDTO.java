package com.okejkadev.clinic_system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.okejkadev.clinic_system.entity.Medication;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicationUnitDTO {
    private Long id;
    private LocalDate expiryDate;
    private String status;
    private MedicationDTO medication;

}
