package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.*;

import com.example.registrationlogindemo.entity.Patient;


import com.example.registrationlogindemo.repository.PatientRepository;
import com.example.registrationlogindemo.service.PatientService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
public class AuthController {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientService patientService;

    @Autowired
    private HttpSession httpSession;
    @GetMapping("/login")
    public String showLoginScreen(){
        return "login";
    }

    @GetMapping("/register")
    public String showregister(){
        return "register";
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PatientDto patientDto) {
        try {
            patientService.registerPatient(patientDto);
            return ResponseEntity.ok().body("{\"message\": \"Registration successful\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Registration failed\"}");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginDto loginDto) {
        try {
            Patient patient = patientService.login(loginDto.getEmail(), loginDto.getPassword());
            if (patient != null) {
                httpSession.setAttribute("patientId", patient.getId());
                httpSession.setAttribute("patientEmail", patient.getEmail());
                System.out.println(httpSession.getAttribute("patientId"));
                System.out.println(httpSession.getAttribute("patientEmail"));
                return ResponseEntity.ok().body("{\"message\": \"Login successful\"}");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid email or password\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Login failed\"}");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        // Invalidate session and logout user
        httpSession.invalidate();
        return "login";
    }










}