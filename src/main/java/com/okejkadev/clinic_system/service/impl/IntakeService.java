package com.okejkadev.clinic_system.service.impl;

import com.okejkadev.clinic_system.dto.IntakeDTO;
import com.okejkadev.clinic_system.dto.PatientDTO;
import com.okejkadev.clinic_system.dto.UserDTO;
import com.okejkadev.clinic_system.dto.request.AddIntakeRequest;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.entity.Intake;
import com.okejkadev.clinic_system.entity.MedicationUnit;
import com.okejkadev.clinic_system.entity.Patient;
import com.okejkadev.clinic_system.entity.User;
import com.okejkadev.clinic_system.exception.CustomException;
import com.okejkadev.clinic_system.repository.IntakeRepository;
import com.okejkadev.clinic_system.repository.MedicationUnitRepository;
import com.okejkadev.clinic_system.repository.PatientRepository;
import com.okejkadev.clinic_system.repository.UserRepository;
import com.okejkadev.clinic_system.service.interf.IIntakeService;
import com.okejkadev.clinic_system.utils.MedicationUnitStatus;
import com.okejkadev.clinic_system.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IntakeService implements IIntakeService {

    @Autowired
    private IntakeRepository intakeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicationUnitRepository medicationUnitRepository;


    @Override
    public Response addIntake(String medicationId, AddIntakeRequest addIntakeRequest) {
        Response response = new Response();
        try {
            Patient patient = patientRepository.findById(addIntakeRequest.getPatientId()).orElseThrow(() -> new CustomException("Patient not found"));
            User clinician = userRepository.findById(addIntakeRequest.getClinicianId()).orElseThrow(() -> new CustomException("Clinician not found"));
            MedicationUnit medicationUnit = medicationUnitRepository.findById(addIntakeRequest.getMedicationUnitId()).orElseThrow(() -> new CustomException("Medication unit not found"));
            if(medicationUnit.getExpiryDate().isBefore(addIntakeRequest.getIntakeDate())){
                throw new CustomException("Medication unit has expired");
            }
            Intake intake = new Intake();
            if(!medicationUnit.getMedication().isActive()) {
                throw new CustomException("Cannot create an intake with medication which is currently inactive");
            }
            if(medicationUnit.getStatus().equals(MedicationUnitStatus.GIVEN.getValue())) {
                throw new CustomException("Medication unit has been already given");
            }
            medicationUnit.setStatus(MedicationUnitStatus.GIVEN.getValue());
            medicationUnitRepository.save(medicationUnit);

            intake.setPatient(patient);
            intake.setMedicationUnit(medicationUnit);
            intake.setClinician(clinician);
            intake.setIntakeDate(LocalDate.now());
            Intake savedIntake = intakeRepository.save(intake);
            response.setStatusCode(200);
            response.setMessage("Intake added successfully");
            response.setIntakeDTO(Utils.mapIntakeEntityToIntakeDTO(savedIntake));
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // Log the stack trace to identify the issue
            response.setStatusCode(500);
            response.setMessage("Error occurred while creating an intake");
        }
        return response;
    }

    @Override
    public Response getAllIntakes(String medicationType, String clinicianFirstName, String clinicianLastName, String patientFirstName, String patientLastName) {
        Response response = new Response();
        try {
            List<Intake> intakes;
            if (medicationType != null || clinicianFirstName != null || clinicianLastName != null || patientFirstName != null || patientLastName != null) {
                intakes = intakeRepository.findAllByFilters(medicationType, clinicianFirstName, clinicianLastName, patientFirstName, patientLastName);
            } else {
                intakes = intakeRepository.findAll();
            }
            List<IntakeDTO> intakeDTOList = Utils.mapIntakeListToIntakeDTOList(intakes);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved intake list ");
            response.setIntakeList(intakeDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during retrieving the intake list");
        }
        return response;
    }


    @Override
    public Response getIntakeById(String intakeId) {
        Response response = new Response();
        try {
            Intake intake = intakeRepository.findById(Long.valueOf(intakeId)).orElseThrow(()-> new CustomException("Intake with " + intakeId + " not found"));
            IntakeDTO intakeDTO = Utils.mapIntakeEntityToIntakeDTO(intake);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved intake with id:" + intakeId);
            response.setIntakeDTO(intakeDTO);
        } catch (CustomException customException) {
            response.setStatusCode(404);
            response.setMessage(customException.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during retrieving intake with id:" + intakeId);
        }
        return response;
    }



    @Override
    public Response getPatientsIntakes(String patientId) {
        Response response = new Response();
        try {
            Patient patient = patientRepository.findById(Long.valueOf(patientId))
                    .orElseThrow(() -> new CustomException("Patient with " + patientId + " not found"));

            List<Intake> patientIntakes = intakeRepository.findByPatientId(Long.valueOf(patientId));
//            if (patientIntakes.isEmpty()) {
//                throw new OurException("No intakes found for the specified patient");
//            }

            // Map patient and intakes separately
            PatientDTO patientDTO = Utils.mapPatientToPatientDTO(patient);
            List<IntakeDTO> intakeDTOList = Utils.mapIntakeListToIntakeDTOList(patientIntakes);

            // Set them in the response
            response.setStatusCode(200);
            response.setMessage("Patient intakes retrieved successfully");
            response.setPatientDTO(patientDTO);
            response.setIntakeList(intakeDTOList);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // Log the stack trace for troubleshooting
            response.setStatusCode(500);
            response.setMessage("Error occurred while retrieving patient intakes");
        }
        return response;
    }

    @Override
    public Response getClinicianIntakes(String clinicianId) {
        Response response = new Response();
        try {
            User clinician = userRepository.findById(Long.valueOf(clinicianId)).orElseThrow(()-> new CustomException("User " + clinicianId + " not found"));
            List<Intake> clinicianIntakes = intakeRepository.findByClinicianId(Long.valueOf(clinicianId));

            UserDTO clinicianDTO = Utils.mapUserEntityToUserDTO(clinician);
            List<IntakeDTO> intakeDTOList = Utils.mapIntakeListToIntakeDTOList(clinicianIntakes);
            response.setStatusCode(200);
            response.setMessage("Clinician intakes retrieved successfully");
            response.setUser(clinicianDTO);
            response.setIntakeList(intakeDTOList);

        } catch (CustomException customException) {
            response.setStatusCode(404);
            response.setMessage(customException.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(500);
            response.setMessage("Error occurred while retrieving clinician intakes");
        }
        return response;
    }


}
