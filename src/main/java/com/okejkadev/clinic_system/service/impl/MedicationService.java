package com.okejkadev.clinic_system.service.impl;

import com.okejkadev.clinic_system.dto.MedicationDTO;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.entity.Medication;
import com.okejkadev.clinic_system.exception.CustomException;
import com.okejkadev.clinic_system.repository.MedicationRepository;
import com.okejkadev.clinic_system.service.AwsS3Service;
import com.okejkadev.clinic_system.service.interf.IMedicationService;
import com.okejkadev.clinic_system.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MedicationService implements IMedicationService {
    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private AwsS3Service awsS3Service;


    @Override
    public Response addMedication(MultipartFile photo, String name, String dosage, String company, String description, int criticalUnitThreshold) {
        Response response = new Response();
        try {
            // Check if the medication already exists
            if (medicationRepository.findByNameAndDosageAndCompany(name, dosage, company).isPresent()) {
                throw new CustomException("A medication with the same name, dosage, and company already exists.");
            }

            Medication medication = new Medication();
            if(photo != null && !photo.isEmpty()){
                String image = awsS3Service.saveImageToS3(photo);
                medication.setMedicationPhotoUrl(image);
            }
            medication.setName(name);
            medication.setDosage(dosage);
            medication.setCompany(company);
            medication.setDescription(description);
            medication.setCriticalUnitThreshold(criticalUnitThreshold);

            Medication savedMedication = medicationRepository.save(medication);
            MedicationDTO medicationDTO = Utils.mapMedicationEntityToMedicationDTO(savedMedication);
            medicationDTO.setUnitCount(medication.getMedicationUnits().size());
            response.setStatusCode(200);
            response.setMessage("Medication added successfully");
            response.setMedication(medicationDTO);
        } catch (CustomException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during adding new medication " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deactivateMedication(Long id) {
        Response response = new Response();
        try {
            Medication medication = medicationRepository.findById(id).orElseThrow(() -> new CustomException("Medication with id " + id + " does not exist."));
            medication.setActive(false);
            Medication deactivatedMedication = medicationRepository.save(medication);
            MedicationDTO medicationDTO = Utils.mapMedicationEntityToMedicationDTO(deactivatedMedication);
            response.setStatusCode(200);
            response.setMessage("Medication deactivated successfully");
            response.setMedication(medicationDTO);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during deactivating Medication " + e.getMessage());
        }
        return response;
    }


    @Override
    public Response reactivateMedication(Long id) {
        Response response = new Response();
        try {
            Medication medication = medicationRepository.findById(id).orElseThrow(() -> new CustomException("Medication with id " + id + " does not exist."));
            medication.setActive(true);
            Medication deactivatedMedication = medicationRepository.save(medication);
            MedicationDTO medicationDTO = Utils.mapMedicationEntityToMedicationDTO(deactivatedMedication);
            response.setStatusCode(200);
            response.setMessage("Medication reactivated successfully");
            response.setMedication(medicationDTO);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during deactivating Medication " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteMedication(Long id) {
        Response response = new Response();
        try {
            medicationRepository.findById(id).orElseThrow(() -> new CustomException("Medication with id " + id + " does not exist."));
            medicationRepository.deleteById(id);
            response.setStatusCode(200);
            response.setMessage("Medication deleted successfully");
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during deletion of Medication " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getFilteredMedications(String name, String dosage, String company, Boolean isActive) {
        Response response = new Response();
        try {
            List<Medication> medications = medicationRepository.findMedicationsByCriteria(name, dosage, company, isActive);
            List<MedicationDTO> medicationDTOList = Utils.mapMedicationListToMedicationDTOList(medications);
            response.setStatusCode(200);
            response.setMessage("Medications found");
            response.setMedicationList(medicationDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during retrieving Medications " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMedicationById(Long id) {
        Response response = new Response();
        try {
            Medication medication = medicationRepository.findById(id).orElseThrow(()-> new CustomException("Medication with id " + id + " does not exist."));
            response.setStatusCode(200);
            response.setMessage("Medication found");
            MedicationDTO medicationDTO = Utils.mapMedicationEntityToMedicationDTO(medication);
            response.setMedication(medicationDTO);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during retrieving Medication " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response changeCriticalUnitThreshold(Long id, int newCriticalThreshold) {
        Response response = new Response();
        try {
            Medication medication = medicationRepository.findById(id).orElseThrow(() -> new CustomException("Medication with id " + id + " does not exist."));
            medication.setCriticalUnitThreshold(newCriticalThreshold);
            medicationRepository.save(medication);
            MedicationDTO medicationDTO = Utils.mapMedicationEntityToMedicationDTO(medication);
            response.setStatusCode(200);
            response.setMessage("Medication critical threshold changed successfully to " + medication.getCriticalUnitThreshold());
            response.setMedication(medicationDTO);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during changing critical threshold for Medication " + e.getMessage());
        }
        return response;
    }


}
