package com.example.registrationlogindemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
public class TechnicianController {

    @GetMapping("/technician")
    public String showtechnician(){
        return "technician";

    }
    @GetMapping("/results")
    public String showTestResultsPage() {
        return "results";
    }
}
