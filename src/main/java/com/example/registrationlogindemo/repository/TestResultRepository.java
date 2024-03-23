package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}
