package com.okejkadev.clinic_system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicationDTO {

    private Long id;
    private String name;
    private String dosage;
    private String company;
    private String description;
    private String medicationPhotoUrl;
    private List<MedicationUnitDTO> listOfMedicationUnits;
    private int unitCount;  // New field for the count of units
    private boolean isActive;
    private int criticalUnitThreshold;

}
