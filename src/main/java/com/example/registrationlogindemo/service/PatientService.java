package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.PatientDto;
import com.example.registrationlogindemo.entity.Patient;

public interface PatientService {
    void registerPatient(PatientDto patientDto);

    Patient login(String email, String password);
}