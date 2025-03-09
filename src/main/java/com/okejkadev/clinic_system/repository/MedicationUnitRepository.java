package com.okejkadev.clinic_system.repository;

import com.okejkadev.clinic_system.entity.Medication;
import com.okejkadev.clinic_system.entity.MedicationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationUnitRepository  extends JpaRepository<MedicationUnit, Long> {

//    @Query("SELECT mu FROM MedicationUnit mu WHERE mu.status = :status")
//    List<MedicationUnit> findByStatus(@Param("status") String status);

    List <MedicationUnit> findByMedicationId(Long medicationId);
    List <MedicationUnit> findByMedicationIdAndStatus(Long medicationId, String status);

    @Query("SELECT mu FROM MedicationUnit mu WHERE mu.id = :unitId AND mu.medication.id = :medicationId")
    Optional<MedicationUnit> findByIdAndMedicationId(@Param("unitId") Long unitId, @Param("medicationId") Long medicationId);
}
