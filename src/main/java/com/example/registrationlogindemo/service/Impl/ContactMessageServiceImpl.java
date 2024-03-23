package com.example.registrationlogindemo.service.Impl;

import com.example.registrationlogindemo.entity.ContactMessage;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.ContactMessageRepository;
import com.example.registrationlogindemo.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository messageRepository;

    @Autowired
    public ContactMessageServiceImpl(ContactMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void saveContactMessage(ContactMessage message) {
        messageRepository.save(message);
    }

    @Override
    public List<ContactMessage> getAllContactMessage() {
        return messageRepository.findAll();
    }

}


