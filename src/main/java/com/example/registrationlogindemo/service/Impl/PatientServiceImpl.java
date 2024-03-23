package com.example.registrationlogindemo.service.Impl;

import com.example.registrationlogindemo.dto.PatientDto;
import com.example.registrationlogindemo.entity.Patient;
import com.example.registrationlogindemo.repository.PatientRepository;
import com.example.registrationlogindemo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void registerPatient(PatientDto patientDto) {
        // Convert PatientDto to Patient entity
        Patient patient = new Patient();
        patient.setEmail(patientDto.getEmail());
        patient.setPassword(patientDto.getPassword());
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setMobile(patientDto.getMobile());

        // Save the patient
        patientRepository.save(patient);
    }

    @Override
    public Patient login(String email, String password) {
        return patientRepository.findByEmailAndPassword(email, password);
    }


}
