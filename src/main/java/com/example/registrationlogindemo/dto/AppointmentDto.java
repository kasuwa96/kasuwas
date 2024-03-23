package com.example.registrationlogindemo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private String appointmentId;
    private String appointmentDate;
    private String appointmentTime;
    private String testType;
    private String status;
    private String report;
}
