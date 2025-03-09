package com.okejkadev.clinic_system.repository;

import com.okejkadev.clinic_system.entity.Intake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IntakeRepository extends JpaRepository<Intake, Long> {

    @Query("SELECT i FROM Intake i " +
            "JOIN i.medicationUnit mu JOIN mu.medication m " +
            "JOIN i.clinician c JOIN i.patient p " +
            "WHERE (:medicationType IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :medicationType, '%'))) " +
            "AND (:clinicianFirstName IS NULL OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :clinicianFirstName, '%'))) " +
            "AND (:clinicianLastName IS NULL OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :clinicianLastName, '%'))) " +
            "AND (:patientFirstName IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('   %', :patientFirstName, '%'))) " +
            "AND (:patientLastName IS NULL OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :patientLastName, '%')))")
    List<Intake> findAllByFilters(
            @Param("medicationType") String medicationType,
            @Param("clinicianFirstName") String clinicianFirstName,
            @Param("clinicianLastName") String clinicianLastName,
            @Param("patientFirstName") String patientFirstName,
            @Param("patientLastName") String patientLastName);


    List<Intake> findByPatientId(Long patientId);
    List<Intake> findByClinicianId(Long userId);

}
