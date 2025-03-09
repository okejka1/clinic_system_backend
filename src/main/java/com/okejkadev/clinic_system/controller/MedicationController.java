package com.okejkadev.clinic_system.controller;

import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.exception.CustomException;
import com.okejkadev.clinic_system.service.interf.IMedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/medications")
public class MedicationController {

    @Autowired
    private IMedicationService medicationService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewMedication(@RequestParam(value = "photo", required = false) MultipartFile photo,
                                                     @RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "dosage", required = false) String dosage,
                                                     @RequestParam(value = "company", required = false) String company,
                                                     @RequestParam(value = "description", required = false) String description,
                                                     @RequestParam(value = "criticalUnitThreshold", required = false) int criticalUnitThreshold) {
        try {
            if (name.isBlank() || dosage.isBlank() || company.isBlank()) {
                Response response = new Response();
                response.setStatusCode(400);
                response.setMessage("Name, dosage, and company are required");
                return ResponseEntity.status(response.getStatusCode()).body(response);
            }
            if(criticalUnitThreshold <= 0) {
                Response response = new Response();
                response.setStatusCode(400);
                response.setMessage("criticalUnitThreshold must be greater than 0");
                return ResponseEntity.status(response.getStatusCode()).body(response);
            }

            Response response = medicationService.addMedication(photo, name, dosage, company, description, criticalUnitThreshold);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (CustomException e) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }

    @PutMapping("/deactivate/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deactivateMedication(@PathVariable Long id) {
        Response response = medicationService.deactivateMedication(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/reactivate/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> reactivateMedication(@PathVariable Long id)
    {
        Response response = medicationService.reactivateMedication(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/critical-threshold/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> reactivateMedication(@PathVariable Long id,
                                                         @RequestParam(value = "criticalUnitThreshold", required = true) int criticalUnitThreshold) {
        if(criticalUnitThreshold <= 0) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("criticalUnitThreshold must be greater than 0");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = medicationService.changeCriticalUnitThreshold(id, criticalUnitThreshold);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteMedication(@PathVariable Long id) {
        Response response = medicationService.deleteMedication(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getFilteredMedications(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String dosage,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) Boolean isActive) {

        Response response = medicationService.getFilteredMedications(name, dosage, company, isActive);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getMedication(@PathVariable Long id) {
        Response response = medicationService.getMedicationById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
