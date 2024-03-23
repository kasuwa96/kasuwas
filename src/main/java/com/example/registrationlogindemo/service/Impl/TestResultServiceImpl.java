package com.example.registrationlogindemo.service.Impl;

import com.example.registrationlogindemo.entity.TestResult;
import com.example.registrationlogindemo.repository.TestResultRepository;
import com.example.registrationlogindemo.service.TestResultService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class TestResultServiceImpl implements TestResultService {


    @Autowired
    private TestResultRepository testResultRepository;

    private final String pdfOutputDirectory = "D:/ABC 2/test-lab/src/main/resources/static/uploads";

    @Override
    public TestResult saveTestResult(TestResult testResult) {
        // Save the test result to the database
        TestResult savedTestResult = testResultRepository.save(testResult);

        // Generate and save PDF document
        generateAndSavePDF(savedTestResult.getId());

        return savedTestResult;
    }

    private void generateAndSavePDF(Long testResultId) {
        // Retrieve the test result from the database
        TestResult testResult = testResultRepository.findById(testResultId)
                .orElseThrow(() -> new RuntimeException("Test result not found with ID: " + testResultId));

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Patient ID: " + testResult.getPatientId());
                contentStream.newLine();
                contentStream.showText("Appointment ID: " + testResult.getAppointmentId());
                contentStream.newLine();
                contentStream.showText("Test Type: " + testResult.getTestType());
                contentStream.newLine();
                contentStream.showText("Test Results: " + testResult.getTestResults());
                contentStream.newLine();
                contentStream.showText("Doctor Name: " + testResult.getDoctorName());
                contentStream.newLine();
                contentStream.showText("Lab Technician Name: " + testResult.getLabTechnicianName());
                contentStream.endText();
            }

            document.save(outputStream);

            // Generate a unique file name
            String fileName = "test_result_" + testResultId + "_" + System.currentTimeMillis() + ".pdf";
            // Save the PDF to disk
            String filePath = pdfOutputDirectory + "/" + fileName;
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                outputStream.writeTo(fileOutputStream);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception properly
        }
    }

    @Override
    public byte[] generatePDF(Long testResultId) {
        // This method is not used for saving PDFs to disk; it's only for downloading PDFs
        throw new UnsupportedOperationException("Method not supported");
    }
}
