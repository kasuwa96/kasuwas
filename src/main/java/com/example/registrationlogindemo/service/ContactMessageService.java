package com.example.registrationlogindemo.service;

 import com.example.registrationlogindemo.entity.ContactMessage;

import java.util.List;

 import com.example.registrationlogindemo.entity.ContactMessage;

 import java.util.List;

public interface ContactMessageService {

    void saveContactMessage(ContactMessage message);

    List<ContactMessage> getAllContactMessage();
}