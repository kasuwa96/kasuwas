package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}

