package com.okejkadev.clinic_system.service.impl;

import com.okejkadev.clinic_system.dto.MedicationUnitDTO;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.entity.Medication;
import com.okejkadev.clinic_system.entity.MedicationUnit;
import com.okejkadev.clinic_system.exception.CustomException;
import com.okejkadev.clinic_system.repository.MedicationRepository;
import com.okejkadev.clinic_system.repository.MedicationUnitRepository;
import com.okejkadev.clinic_system.service.interf.IMedicationUnitService;
import com.okejkadev.clinic_system.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicationUnitService implements IMedicationUnitService {

    @Autowired
    private MedicationUnitRepository medicationUnitRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    public Response addMedicationUnit(Long medicationId, LocalDate expiryDate, String status, int quantity) {
        Response response = new Response();
        try {
            // Adjust for date-only comparison, ignoring time component
            LocalDate today = LocalDate.now(ZoneId.systemDefault());

            // Ensure expiryDate is in the future without considering time of day
            if (!expiryDate.isAfter(today)) { // Compare using date-only
                throw new CustomException("Expiry date must be in the future");
            }

            Medication medication = medicationRepository.findById(medicationId).orElseThrow(()-> new CustomException("Unable to find this type of medication"));
            List<MedicationUnit> medicationUnits = new ArrayList<>();
            for (int i = 0; i < quantity; i++) {
                MedicationUnit medicationUnit = new MedicationUnit();
                medicationUnit.setStatus(status);
                medicationUnit.setMedication(medication);
                medicationUnit.setExpiryDate(expiryDate);
                medicationUnits.add(medicationUnit);
            }
            List<MedicationUnit> savedUnits = medicationUnitRepository.saveAll(medicationUnits);

            List<MedicationUnitDTO> medicationUnitDTOList = savedUnits.stream()
                    .map(Utils::mapMedicationUnitEntityToMedicationUnitDTO)
                    .toList();

            response.setStatusCode(200);
            response.setMessage("Successfully added medication units");
            response.setMedicationUnitList(medicationUnitDTOList);
        } catch (CustomException customException) {
            response.setStatusCode(400);
            response.setMessage(customException.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            if(expiryDate.isBefore(LocalDate.now()))
                response.setMessage("An error occurred during adding medication units");

        }
        return response;
    }

    @Override
    public Response deleteMedicationUnit(Long medicationId, Long unitId) {
        Response response = new Response();
        try {
            MedicationUnit unit = medicationUnitRepository.findByIdAndMedicationId(unitId, medicationId)
                    .orElseThrow(() -> new CustomException("Medication Unit not found for the given medication."));

            medicationUnitRepository.delete(unit);
            response.setStatusCode(200);
            response.setMessage("Medication Unit deleted successfully.");
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during deletion of Medication Unit: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getFilteredMedicationUnits(Long medicationId, String status) {
        Response response = new Response();
        try {
            List<MedicationUnit> medicationUnitList;

            if (status != null && !status.isEmpty()) {
                medicationUnitList = medicationUnitRepository.findByMedicationIdAndStatus(medicationId, status);
            } else {
                medicationUnitList = medicationUnitRepository.findByMedicationId(medicationId);
            }

            List<MedicationUnitDTO> medicationUnitDTOList = Utils.mapMedicationUnitListToMedicationUnitListDTO(medicationUnitList);
            response.setStatusCode(200);
            response.setMessage("Medication Units found");
            response.setMedicationUnitList(medicationUnitDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while retrieving Medication Units: " + e.getMessage());
        }
        return response;
    }


}
