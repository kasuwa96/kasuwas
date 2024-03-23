package com.example.registrationlogindemo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "test")
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "patientId")
    private Long patientId;
    @Column(name = "appointmentId")
    private Long appointmentId;
    @Column(name = "testType")
    private String testType;
    @Column(name = "testResults")
    private String testResults;
    @Column(name = "doctorName")
    private String doctorName;
    @Column(name = "labTechnicianName")
    private String labTechnicianName;

    // Getters and setters
}
