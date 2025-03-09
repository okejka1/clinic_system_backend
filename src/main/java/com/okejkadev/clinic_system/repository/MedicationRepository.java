package com.okejkadev.clinic_system.repository;

import com.okejkadev.clinic_system.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {


    Optional<Medication> findByNameAndDosageAndCompany(String name, String dosage, String company);


    @Query("SELECT COUNT(mu) FROM MedicationUnit mu WHERE mu.medication.id = :medicationId")
    long countMedicationUnitsByMedicationId(@Param("medicationId") Long medicationId);

    @Query("SELECT m FROM Medication m " +
            "WHERE (:name IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:dosage IS NULL OR LOWER(m.dosage) LIKE LOWER(CONCAT('%', :dosage, '%'))) " +
            "AND (:company IS NULL OR LOWER(m.company) LIKE LOWER(CONCAT('%', :company, '%'))) " +
            "AND (:isActive IS NULL OR m.isActive = :isActive)")
    List<Medication> findMedicationsByCriteria(
            @Param("name") String name,
            @Param("dosage") String dosage,
            @Param("company") String company,
            @Param("isActive") Boolean isActive);
}
