package com.okejkadev.clinic_system.controller;

import com.okejkadev.clinic_system.dto.BulkMedicationUnitRequestDTO;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.entity.MedicationUnit;
import com.okejkadev.clinic_system.service.interf.IMedicationUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/medication-units")
public class MedicationUnitController {

    @Autowired
    private IMedicationUnitService medicationUnitService;


    @PostMapping("/medication/{medicationId}/units")
    public ResponseEntity<Response> addMedicationUnit(
            @PathVariable Long medicationId,
            @RequestBody BulkMedicationUnitRequestDTO bulkRequest
    ) {
        Response response = medicationUnitService.addMedicationUnit(
                medicationId,
                bulkRequest.getExpiryDate(),
                bulkRequest.getStatus(),
                bulkRequest.getQuantity());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @DeleteMapping("/medication/{medicationId}/units/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteMedicationUnit(
            @PathVariable Long medicationId,
            @PathVariable Long id) {

        Response response = medicationUnitService.deleteMedicationUnit(medicationId, id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/medication/{medicationId}/units")
    public ResponseEntity<Response> getMedicationUnits(
            @PathVariable Long medicationId,          // medicationId as a path variable
            @RequestParam(required = false) String status  // optional status filter
    ) {
        Response response = medicationUnitService.getFilteredMedicationUnits(medicationId, status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
