package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.entity.TestResult;
import com.example.registrationlogindemo.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/test-results")
public class TestResultController {

    @Autowired
    private TestResultService testResultService;

    @PostMapping
    public ResponseEntity<TestResult> createTestResult(@RequestBody TestResult testResult) {
        TestResult savedTestResult = testResultService.saveTestResult(testResult);
        return ResponseEntity.ok(savedTestResult);
    }

    @GetMapping("/{testResultId}/pdf")
    public ResponseEntity<byte[]> generatePDF(@PathVariable Long testResultId) {
        byte[] pdfBytes = testResultService.generatePDF(testResultId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
    }

}
