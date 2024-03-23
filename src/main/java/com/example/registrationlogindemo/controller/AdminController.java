package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.ContactMessage;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.ContactMessageRepository;
import com.example.registrationlogindemo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.registrationlogindemo.dto.AppointmentDto;
import com.example.registrationlogindemo.entity.Appointment;
import com.example.registrationlogindemo.service.AppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
public class AdminController {



    @Autowired
    private UserService userService;
    @GetMapping("/adminLogin")
    public String showadminLogin(){
        return "adminlogin";
    }

    @GetMapping("/admin")
    public String showadmin(){
        return "admin";
    }



    @PostMapping("/userLogin")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        User user = userService.loginUser(userDto);
        if (user != null) {
            return ResponseEntity.ok(UserDto.fromUser(user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/test")
    public String showTestResults(HttpSession session, Model model) {
        Integer patientId = (Integer) session.getAttribute("patientId");
        if (patientId == null) {
            return "redirect:/login";
        }
        model.addAttribute("patientId", patientId);
        System.out.println("patient id"+patientId);
        return "test";
    }


    @GetMapping("/userlogout")
    public String logout(HttpSession httpSession) {
        // Invalidate session and logout user
        httpSession.invalidate();
        return "adminLogin";
    }


@GetMapping("/index")
    public String showhome(){
        return "index";
    }
}
