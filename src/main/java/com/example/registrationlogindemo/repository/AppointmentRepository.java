package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {



    List<Appointment> findByPatientId(Integer patientId);
    Appointment findByAppointmentId(int appointmentId);


    Appointment findByPatientIdAndAppointmentId(int patientId, int appointmentId);
}