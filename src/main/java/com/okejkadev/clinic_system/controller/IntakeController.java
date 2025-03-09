package com.okejkadev.clinic_system.controller;

import com.okejkadev.clinic_system.dto.request.AddIntakeRequest;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.service.interf.IIntakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/intakes")
public class IntakeController {

    @Autowired
    private IIntakeService intakeService;

    @PostMapping("/add/{medicationId}")
    public ResponseEntity<Response> createIntake(@PathVariable String medicationId,
                                                 @RequestBody AddIntakeRequest addIntakeRequest) {
        Response response = intakeService.addIntake(medicationId, addIntakeRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getIntakes(@RequestParam(required = false) String medicationType,
                                               @RequestParam(required = false) String clinicianFirstName,
                                               @RequestParam(required = false) String clinicianLastName,
                                               @RequestParam(required = false) String patientFirstName,
                                               @RequestParam(required = false) String patientLastName) {
        Response response = intakeService.getAllIntakes(medicationType, clinicianFirstName, clinicianLastName, patientFirstName, patientLastName);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getIntakeById(@PathVariable String id) {
        Response response = intakeService.getIntakeById(id);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Response> getPatientIntakes(@PathVariable String patientId) {
        Response response = intakeService.getPatientsIntakes(patientId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("user/{clinicianId}")
    public ResponseEntity<Response> getClinicianIntakes(@PathVariable String clinicianId) {
        Response response = intakeService.getClinicianIntakes(clinicianId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}
