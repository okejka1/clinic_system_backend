package com.okejkadev.clinic_system.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.okejkadev.clinic_system.dto.*;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Response {

    private int statusCode;
    private String message;
    private String token;
    private String role;
    private String expirationTime;

    private UserDTO user;
    private MedicationDTO medication;
    private MedicationUnitDTO medicationUnit;
    private PatientDTO patientDTO;
    private IntakeDTO intakeDTO;

    List<UserDTO> userList;
    List<MedicationDTO> medicationList;
    List<MedicationUnitDTO> medicationUnitList;
    List<PatientDTO> patientList;
    List<IntakeDTO> intakeList;

}
