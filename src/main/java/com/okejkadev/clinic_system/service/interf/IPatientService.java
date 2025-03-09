package com.okejkadev.clinic_system.service.interf;

import com.okejkadev.clinic_system.dto.response.Response;

import java.time.LocalDate;

public interface IPatientService {

    Response addPatient(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String medicalHistory);

    Response getAllPatients(String name);

    Response deletePatient(String patientId);

    Response getPatientById(String patientId);

    Response updateMedicalHistoryPatient(String patientId, String medicalHistory);
}
