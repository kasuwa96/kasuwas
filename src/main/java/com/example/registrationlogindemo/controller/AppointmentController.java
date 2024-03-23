package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.AppointmentDto;
import com.example.registrationlogindemo.entity.Appointment;
import com.example.registrationlogindemo.service.AppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@Controller
public class AppointmentController {



    @Autowired
    private  AppointmentService appointmentService;


    @Autowired
    private HttpSession httpSession;

    @PostMapping("/make")
    public ResponseEntity<String> makeAppointment(@RequestBody AppointmentDto appointmentDto,
                                  HttpSession httpSession) {
        try {
            // Retrieve patient ID and email from the session
            Integer patientIdInteger = (Integer) httpSession.getAttribute("patientId");
            String patientEmail = (String) httpSession.getAttribute("patientEmail");

            // Convert patient ID to Long
            Long patientId = patientIdInteger.longValue();

            // Ensure patientId and patientEmail are not null
            if (patientId == null || patientEmail == null) {
                throw new RuntimeException("Patient ID or email is missing in session");
            }
            appointmentDto.setStatus("Pending");
            appointmentDto.setReport(null);

            // Call the service method passing appointmentDto, patientId, and patientEmail
            appointmentService.makeAppointment(appointmentDto, patientId, patientEmail);

            return ResponseEntity.ok("Appointment created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create appointment");
        }
    }

    @GetMapping("/appointment")
    public String showAppointmentPage() {
        return "appointment"; // Return the appointment.html Thymeleaf template
    }

    @PostMapping("/appointments/{appointmentId}/uploadReport")
    public ResponseEntity<String> uploadReport(@PathVariable int appointmentId,
                                               @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("appointmentid"+appointmentId);
        appointmentService.uploadReport(appointmentId, file);
        return ResponseEntity.ok("File uploaded successfully");
    }


    @GetMapping("/view")
    public String getAppointments(Model model, HttpSession session) {
        Integer patientId = (Integer) session.getAttribute("patientId");
        System.out.println("session Id"+patientId);
        if (patientId != null) {
            List<Appointment> appointments = appointmentService.getAppointmentsByUserId(patientId);
            model.addAttribute("appointments", appointments);
            return "view";
        } else {
            return "/login"; // Redirect to login if user not logged in
        }
    }

    @GetMapping("/api/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }


    // Endpoint to fetch appointments for a patient
    @GetMapping("/{patientId}/appointments")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsForPatient(@PathVariable int patientId) {
        List<AppointmentDto> appointments = appointmentService.getAppointmentsForPatient(patientId);
        return ResponseEntity.ok(appointments);
    }

    // Endpoint to view report for a specific appointment
    @GetMapping("/{patientId}/appointments/{appointmentId}/viewReport")
    public ResponseEntity<byte[]> viewReport(@PathVariable int patientId, @PathVariable int appointmentId) {
        byte[] report = appointmentService.getReportForAppointment(patientId, appointmentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return ResponseEntity.ok().headers(headers).body(report);
    }
    // Endpoint to download report for a specific appointment
    @GetMapping("/{patientId}/appointments/{appointmentId}/downloadReport")
    public ResponseEntity<byte[]> downloadReport(@PathVariable int patientId, @PathVariable int appointmentId) {
        byte[] report = appointmentService.getReportForAppointment(patientId, appointmentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "report.pdf");

        return ResponseEntity.ok().headers(headers).body(report);
    }

    @RequestMapping(value = "/appointments/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void cancelAppointment(@PathVariable("id") int id) {
        appointmentService.cancelAppointment(id);
    }
}
