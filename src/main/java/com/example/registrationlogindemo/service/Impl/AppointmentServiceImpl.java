package com.example.registrationlogindemo.service.Impl;

import com.example.registrationlogindemo.dto.AppointmentDto;
import com.example.registrationlogindemo.entity.Appointment;
import com.example.registrationlogindemo.repository.AppointmentRepository;
import com.example.registrationlogindemo.service.AppointmentService;
import com.example.registrationlogindemo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {


    private final AppointmentRepository appointmentRepository;
    private final EmailService emailService;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, EmailService emailService) {
        this.appointmentRepository = appointmentRepository;
        this.emailService = emailService;
    }

    @Override
    public void makeAppointment(AppointmentDto appointmentDto, Long patientId, String patientEmail) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(LocalDate.parse(appointmentDto.getAppointmentDate()));
        appointment.setAppointmentTime(LocalTime.parse(appointmentDto.getAppointmentTime()));
        appointment.setTestType(appointmentDto.getTestType());
        appointment.setPatientId(Math.toIntExact(patientId));
        appointment.setPatientEmail(patientEmail);
        appointment.setStatus(appointmentDto.getStatus());
        appointment.setReport(appointmentDto.getReport());
        System.out.println("appointment" + appointment);
        appointmentRepository.save(appointment);


        String laboratoryName = "ABC Laboratory";
        String laboratoryEmail = "abclab@example.com";
        String laboratoryPhoneNumber = "077 123 4567";

        String to = patientEmail;
        String subject = "Appointment Details from " + laboratoryName;
        String text = "Dear Patient,\n\n" +
                "Thank you for choosing " + laboratoryName + " for your appointment. Here are the details:\n\n" +
                "Patient ID: " + appointment.getPatientId() + "\n" +
                "Appointment ID: " + appointment.getAppointmentId() + "\n" +
                "Appointment Date: " + appointment.getAppointmentDate() + "\n" +
                "Appointment Time: " + appointment.getAppointmentTime() + "\n" +
                "Test Type: " + appointment.getTestType() + "\n\n" +
                "Please feel free to contact us if you have any questions or need further assistance.\n\n" +
                "Sincerely,\n" +
                laboratoryName + " Laboratory\n" +
                laboratoryEmail + "\n" +
                laboratoryPhoneNumber;

        emailService.sendAppointmentDetailsEmail(to, subject, text);
    }

        @Override
    public List<Appointment> getAppointmentsByUserId(Integer patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Override
    public void uploadReport(int appointmentId, MultipartFile report) throws IOException {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
        if (appointment != null) {
            if (!report.isEmpty()) {
                // Get the file name
                String fileName = report.getOriginalFilename();
                // Set the path where the file will be saved
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                // Save the file to the specified directory
                Files.copy(report.getInputStream(), filePath);
                // Set the report path in the appointment entity
                appointment.setReport(filePath.toString());
                appointment.setStatus("Completed");
                // Update the appointment in the database
                appointmentRepository.save(appointment);
            } else {
                throw new IllegalArgumentException("Report file is empty");
            }
        } else {
            throw new IllegalArgumentException("Appointment not found with ID: " + appointmentId);
        }


    }

    @Override
    public List<AppointmentDto> getAppointmentsForPatient(int patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointmentDto appointmentDto = convertToDto(appointment);
            appointmentDtos.add(appointmentDto);
        }
        return appointmentDtos;
    }

    private AppointmentDto convertToDto(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        // Assuming you have appropriate getter methods in Appointment entity
        appointmentDto.setAppointmentId(String.valueOf(appointment.getAppointmentId()));
        appointmentDto.setTestType(appointment.getTestType());
        appointmentDto.setStatus(appointment.getStatus());
        return appointmentDto;
    }

    @Override
    public byte[] getReportForAppointment(int patientId, int appointmentId) {
        Appointment appointment = appointmentRepository.findByPatientIdAndAppointmentId(patientId, appointmentId);
        if (appointment != null) {
            String reportPath = appointment.getReport(); // Assuming the report path is stored as a String
            Path filePath = Paths.get(reportPath);
            try {
                return Files.readAllBytes(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Appointment not found with ID: " + appointmentId);
        }
    }

    @Override
    public void cancelAppointment(int id) {
        appointmentRepository.deleteById(Long.valueOf(id));
    }


    }

