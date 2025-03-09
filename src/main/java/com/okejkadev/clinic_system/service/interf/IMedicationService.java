package com.okejkadev.clinic_system.service.interf;


import com.okejkadev.clinic_system.dto.response.Response;
import org.springframework.web.multipart.MultipartFile;



public interface IMedicationService {

    Response addMedication(MultipartFile photo, String name, String dosage, String company, String description, int criticalUnitThreshold);
    Response deactivateMedication(Long id);
    Response reactivateMedication(Long id);
    Response deleteMedication(Long id);
    Response getFilteredMedications(String name, String dosage, String company, Boolean isActive);
    Response getMedicationById(Long id);
    Response changeCriticalUnitThreshold(Long id, int newCriticalThreshold);
}
