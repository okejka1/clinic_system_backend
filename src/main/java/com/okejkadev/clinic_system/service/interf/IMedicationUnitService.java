package com.okejkadev.clinic_system.service.interf;

import com.okejkadev.clinic_system.dto.response.Response;

import java.time.LocalDate;

public interface IMedicationUnitService {

    Response addMedicationUnit(Long medicationId, LocalDate expiryDate, String status, int quantity);

    Response deleteMedicationUnit(Long medicationId, Long unitId);

    Response getFilteredMedicationUnits(Long medicationId, String status);
}
