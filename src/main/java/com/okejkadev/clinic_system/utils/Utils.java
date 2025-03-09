package com.okejkadev.clinic_system.utils;

import com.okejkadev.clinic_system.dto.*;
import com.okejkadev.clinic_system.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static UserDTO mapUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }


    public static MedicationDTO mapMedicationEntityToMedicationDTO(Medication medication) {
        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setId(medication.getId());
        medicationDTO.setName(medication.getName());
        medicationDTO.setDosage(medication.getDosage());
        medicationDTO.setDescription(medication.getDescription());
        medicationDTO.setCompany(medication.getCompany());
        medicationDTO.setMedicationPhotoUrl(medication.getMedicationPhotoUrl());
        int unitCount = medication.getMedicationUnits() != null ? medication.getMedicationUnits().size() : 0;
        medicationDTO.setUnitCount(unitCount);
        medicationDTO.setActive(medication.isActive());
        medicationDTO.setCriticalUnitThreshold(medication.getCriticalUnitThreshold());
        return medicationDTO;
    }

    public static MedicationUnitDTO mapMedicationUnitEntityToMedicationUnitDTO(MedicationUnit medicationUnit) {
        MedicationUnitDTO medicationUnitDTO = new MedicationUnitDTO();
        medicationUnitDTO.setId(medicationUnit.getId());
        medicationUnitDTO.setExpiryDate(medicationUnit.getExpiryDate());
        medicationUnitDTO.setStatus(medicationUnit.getStatus());
        medicationUnitDTO.setMedication(Utils.mapMedicationEntityToMedicationDTO(medicationUnit.getMedication()));
        return medicationUnitDTO;
    }

    public static PatientDTO mapPatientToPatientDTO(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setFirstName(patient.getFirstName());
        patientDTO.setLastName(patient.getLastName());
        patientDTO.setBirthDate(patient.getBirthDate());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());
        patientDTO.setMedicalHistory(patient.getMedicalHistory());
        return patientDTO;
    }

    public static PatientDTO mapPatientEntityToPatientDTOPlusIntakes(Patient patient) {
        PatientDTO patientDTO = Utils.mapPatientToPatientDTO(patient);
        patientDTO.setIntakeList(Utils.mapIntakeListToIntakeDTOList(patient.getIntakes()));
        return patientDTO;
    }


    public static IntakeDTO mapIntakeEntityToIntakeDTO(Intake intake) {
        IntakeDTO intakeDTO = new IntakeDTO();
        intakeDTO.setId(intake.getId());
        intakeDTO.setIntakeDate(intake.getIntakeDate());
        intakeDTO.setClinician(Utils.mapUserEntityToUserShortDTO(intake.getClinician()));
        intakeDTO.setPatient(Utils.mapPatientEntityToPatientShortDTO(intake.getPatient()));
        intakeDTO.setMedicationUnit(Utils.mapMedicationUnitEntityToMedicationUnitDTO(intake.getMedicationUnit()));
        return intakeDTO;
    }

    public static UserShortDTO mapUserEntityToUserShortDTO(User user){
        UserShortDTO userShortDTO = new UserShortDTO();
        userShortDTO.setId(user.getId());
        userShortDTO.setFirstName(user.getFirstName());
        userShortDTO.setLastName(user.getLastName());
        return userShortDTO;
    }

    public static PatientShortDTO mapPatientEntityToPatientShortDTO(Patient patient){
        PatientShortDTO patientShortDTO = new PatientShortDTO();
        patientShortDTO.setId(patient.getId());
        patientShortDTO.setFirstName(patient.getFirstName());
        patientShortDTO.setLastName(patient.getLastName());
        return patientShortDTO;
    }

    public static List<IntakeDTO> mapIntakeListToIntakeDTOList(List<Intake> intakeList) {
        return intakeList.stream().map(Utils::mapIntakeEntityToIntakeDTO).collect(Collectors.toList());
    }



    public static List<MedicationUnitDTO> mapMedicationUnitListToMedicationUnitListDTO(List<MedicationUnit> medicationUnits) {
        return medicationUnits.stream().map(Utils::mapMedicationUnitEntityToMedicationUnitDTO).collect(Collectors.toList());
    }

    public static List<UserDTO> mapUserListToUserDTOList(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<MedicationDTO> mapMedicationListToMedicationDTOList(List<Medication> medicationList) {
        return medicationList.stream().map(Utils::mapMedicationEntityToMedicationDTO).collect(Collectors.toList());
    }

    public static List<PatientDTO> mapPatientListToPatientDTOList(List<Patient> patientList){
        return patientList.stream().map(Utils::mapPatientToPatientDTO).collect(Collectors.toList());
    }
}
