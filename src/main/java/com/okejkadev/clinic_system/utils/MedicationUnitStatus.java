package com.okejkadev.clinic_system.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MedicationUnitStatus {
    AVAILABLE("available"),
    GIVEN("given");

    private final String value;

}
