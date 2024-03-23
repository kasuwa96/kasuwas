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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class TestResultServiceImpl implements TestResultService {

    @Autowired
    private TestResultRepository testResultRepository;

    private final String pdfOutputDirectory = "C:/Users/RavinduK/OneDrive/Desktop/advance programming/test-lab/src/main/resources/static/uploads";

    @Override
    public TestResult saveTestResult(TestResult testResult) {
        return testResultRepository.save(testResult);
    }

    @Override
    public byte[] generatePDF(Long testResultId) {
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

            // Save the PDF to disk
            String filePath = pdfOutputDirectory + "/test_result_" + testResultId + ".pdf";
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                outputStream.writeTo(fileOutputStream);
            }

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception properly
            return null;
        }
    }
}


