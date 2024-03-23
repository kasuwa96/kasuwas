package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.entity.TestResult;

public interface TestResultService {
    TestResult saveTestResult(TestResult testResult);
    byte[] generatePDF(Long testResultId);
}
