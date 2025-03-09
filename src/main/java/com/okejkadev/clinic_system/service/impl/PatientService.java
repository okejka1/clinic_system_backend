package com.okejkadev.clinic_system.service.impl;

import com.okejkadev.clinic_system.dto.PatientDTO;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.entity.Patient;
import com.okejkadev.clinic_system.exception.CustomException;
import com.okejkadev.clinic_system.repository.PatientRepository;
import com.okejkadev.clinic_system.service.interf.IPatientService;
import com.okejkadev.clinic_system.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Response addPatient(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String medicalHistory) {
        Response response = new Response();
        try {
            Optional<Patient> existingPatient = patientRepository.findByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);

            if (existingPatient.isPresent()) {
                response.setStatusCode(409); // Conflict status
                response.setMessage("Patient with the same details already exists.");
                return response;
            }

            Patient patient = new Patient();
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setBirthDate(birthDate);
            patient.setPhoneNumber(phoneNumber);
            patient.setMedicalHistory(medicalHistory);
            Patient savedPatient = patientRepository.save(patient);
            PatientDTO patientDTO = Utils.mapPatientToPatientDTO(savedPatient);
            response.setStatusCode(200);
            response.setMessage("Patient added successfully");
            response.setPatientDTO(patientDTO);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Patient could not be added");
        }
        return response;
    }

    @Override
    public Response getAllPatients(String name) {
        Response response = new Response();
        try {
            List<Patient> patients;
            if (name != null && !name.isBlank()) {
                patients = patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
            } else {
                patients = patientRepository.findAll();
            }
            List<PatientDTO> patientDTOList = Utils.mapPatientListToPatientDTOList(patients);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved all patients");
            response.setPatientList(patientDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during retrieve all patients + e.getMessage()");
        }
        return response;
    }

    @Override
    public Response deletePatient(String patientId) {
        Response response = new Response();
        try {
            patientRepository.findById(Long.valueOf(patientId)).orElseThrow(() -> new CustomException("Patient not found"));
            patientRepository.deleteById(Long.valueOf(patientId));
            response.setStatusCode(200);
            response.setMessage("Patient deleted successfully");
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during deleting patient " + patientId);
        }
        return response;
    }

    @Override
    public Response getPatientById(String patientId) {
        Response response = new Response();
        try {
            Patient patient = patientRepository.findById(Long.valueOf(patientId)).orElseThrow(() -> new CustomException("Patient not found "));
            PatientDTO patientDTO = Utils.mapPatientToPatientDTO(patient);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved patient of id: " + patientId);
            response.setPatientDTO(patientDTO);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during retrieving patient " + patientId);
        }
        return response;
    }

    @Override
    public Response updateMedicalHistoryPatient(String patientId, String medicalHistory) {
        Response response = new Response();
        try {
            Patient patient = patientRepository.findById(Long.valueOf(patientId)).orElseThrow(() -> new CustomException("Patient not found "));
            patient.setMedicalHistory(medicalHistory);
            Patient savedPatient = patientRepository.save(patient);
            PatientDTO patientDTO = Utils.mapPatientToPatientDTO(savedPatient);
            response.setStatusCode(200);
            response.setMessage("Patient updated successfully");
            response.setPatientDTO(patientDTO);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during updating patient " + patientId);
        }
        return response;
    }


}
