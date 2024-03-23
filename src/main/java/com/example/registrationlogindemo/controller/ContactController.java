package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.entity.ContactMessage;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.ContactMessageRepository;
import com.example.registrationlogindemo.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    private final ContactMessageService messageService;


    @Autowired
    public ContactController(ContactMessageService messageService) {
        this.messageService = messageService;

    }

    @PostMapping("/contact")
    public String submitContactForm(@RequestBody ContactMessage message) {
        messageService.saveContactMessage(message);
        return "redirect:/index"; // Redirect to homepage or any other page
    }
    @GetMapping("/inquiry")
    public ResponseEntity<List<ContactMessage>> getAllContactMessage() {
        List<ContactMessage> messageList = messageService.getAllContactMessage();
        System.out.println("contactMessage" + messageList);
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }


}
