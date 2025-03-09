package com.okejkadev.clinic_system.service.interf;

import com.okejkadev.clinic_system.dto.request.AddIntakeRequest;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.entity.Intake;

public interface IIntakeService {

    Response addIntake(String medicationId, AddIntakeRequest addIntakeRequest);
    Response getAllIntakes(String medicationType, String clinicianFirstName, String clinicianLastName, String patientFirstName, String patientLastName);
    Response getIntakeById(String intakeId);
    Response getPatientsIntakes(String patientId);
    Response getClinicianIntakes(String clinicianId);

}
