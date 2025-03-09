package com.okejkadev.clinic_system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalHistoryDTO {

    String medicalHistory;
}
