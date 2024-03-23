package com.example.registrationlogindemo.service;

public interface EmailService {
    void sendAppointmentDetailsEmail(String to, String subject, String text);
}
