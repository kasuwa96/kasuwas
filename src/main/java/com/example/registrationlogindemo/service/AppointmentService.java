package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.AppointmentDto;
import com.example.registrationlogindemo.entity.Appointment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AppointmentService {

    void makeAppointment(AppointmentDto appointmentDto, Long patientId, String patientEmail);

    List<Appointment> getAppointmentsByUserId(Integer patientId);

    List<Appointment> getAllAppointments();

    void uploadReport(int appointmentId, MultipartFile report) throws IOException;

    List<AppointmentDto> getAppointmentsForPatient(int patientId);
    byte[] getReportForAppointment(int patientId, int appointmentId);

    void cancelAppointment(int id);
    
}
