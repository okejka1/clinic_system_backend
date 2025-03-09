package com.okejkadev.clinic_system.controller;

import com.okejkadev.clinic_system.dto.MedicalHistoryDTO;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.entity.Patient;
import com.okejkadev.clinic_system.entity.User;
import com.okejkadev.clinic_system.service.interf.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private IPatientService patientService;

    @PostMapping("/add-patients")
    public ResponseEntity<Response> addPatient(@RequestBody Patient patient) {
        Response response = patientService.addPatient(
                patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthDate(),
                patient.getPhoneNumber(),
                patient.getMedicalHistory());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllPatients(@RequestParam(required = false) String name) {
        Response response = patientService.getAllPatients(name);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<Response> deletePatient(@PathVariable("patientId") String patientId) {
        Response response = patientService.deletePatient(patientId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-id/{patientId}")
    public ResponseEntity<Response> getPatientById(@PathVariable("patientId") String patientId) {
        Response response = patientService.getPatientById(patientId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update-medical-history/{patientId}")
    public ResponseEntity<Response> updatePatientMedicalHistory(
            @PathVariable("patientId") String patientId,
            @RequestBody MedicalHistoryDTO medicalHistoryDTO) {
        Response response = patientService.updateMedicalHistoryPatient(patientId, medicalHistoryDTO.getMedicalHistory());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
