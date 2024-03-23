package com.example.registrationlogindemo.service.Impl;

import com.example.registrationlogindemo.dto.PatientDto;
import com.example.registrationlogindemo.entity.Patient;
import com.example.registrationlogindemo.repository.PatientRepository;
import com.example.registrationlogindemo.service.PatientService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void registerPatient(PatientDto patientDto) {
        // Check for null or empty values
        if (StringUtils.isEmpty(patientDto.getEmail()) || StringUtils.isEmpty(patientDto.getPassword()) ||
                StringUtils.isEmpty(patientDto.getFirstName()) || StringUtils.isEmpty(patientDto.getLastName()) ||
                StringUtils.isEmpty(patientDto.getMobile())) {
            throw new IllegalArgumentException("All fields are required.");
        }

        // Validate email format
        if (!EmailValidator.getInstance().isValid(patientDto.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // Check if email is already registered
        if (patientRepository.existsByEmail(patientDto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        // Validate password complexity
        if (!isValidPassword(patientDto.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and " +
                    "contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
        }

        // Save the patient
        Patient patient = new Patient();
        patient.setEmail(patientDto.getEmail());
        patient.setPassword(patientDto.getPassword());
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setMobile(patientDto.getMobile());

        patientRepository.save(patient);
    }

    @Override
    public Patient login(String email, String password) {
        return patientRepository.findByEmailAndPassword(email, password);
    }

    // Method to validate password
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";
        return password.matches(regex);
    }
}
