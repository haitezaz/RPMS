package backend;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;

import java.io.FileNotFoundException;
import java.io.IOException; 
import java.time.format.DateTimeFormatter;
import java.util.List;


public class PDFGeneration {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void generateVitalsReport(int patientId, String filePath) throws IOException {
        List<Vitals> vitalsList = VitalsDAO.getVitalsByPatientId(patientId);

        try (PdfWriter writer = new PdfWriter(filePath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            document.add(new Paragraph("Vitals Report for Patient ID: " + patientId).setBold().setFontSize(16));
            document.add(new Paragraph("Generated on: " + java.time.LocalDateTime.now().format(dtf)).setFontSize(10));
            document.add(new Paragraph("\n"));

            for (Vitals v : vitalsList) {
                document.add(new Paragraph("Date: " + (v.getDateTime() != null ? v.getDateTime().format(dtf) : "N/A")));
                document.add(new Paragraph("Heart Rate: " + v.getHeartRate()));
                document.add(new Paragraph("Blood Pressure: " + v.getSystolicBloodPressure() + "/" + v.getDiastolicBloodPressure()));
                document.add(new Paragraph("Oxygen Level: " + v.getOxygenLevel()));
                document.add(new Paragraph("Temperature: " + v.getTemperature()));
                document.add(new Paragraph("--------------------------------------------------"));
            }

            System.out.println("Vitals PDF report generated at " + filePath);

        } catch (FileNotFoundException e) {
            System.err.println("Error generating vitals report: " + e.getMessage());
        }
    }

    public static void generateFeedbackReport(int patientId, String filePath) throws IOException {
        List<Feedback> feedbackList = FeedbackDAO.getFeedbacksForPatient(patientId);

        try (PdfWriter writer = new PdfWriter(filePath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            document.add(new Paragraph("Doctor Feedback Report for Patient ID: " + patientId).setBold().setFontSize(16));
            document.add(new Paragraph("Generated on: " + java.time.LocalDateTime.now().format(dtf)).setFontSize(10));
            document.add(new Paragraph("\n"));

            for (Feedback fb : feedbackList) {
                document.add(new Paragraph("Date: " + (fb.getFeedbackDateTime() != null ? fb.getFeedbackDateTime().format(dtf) : "N/A")));
                document.add(new Paragraph("Doctor ID: " + fb.getDoctorID()));
                document.add(new Paragraph("Feedback: " + fb.getFeedback()));
                document.add(new Paragraph("Medication: " + fb.getMedication()));
                document.add(new Paragraph("--------------------------------------------------"));
            }

            System.out.println("Feedback PDF report generated at " + filePath);

        } catch (FileNotFoundException e) {
            System.err.println("Error generating feedback report: " + e.getMessage());
        }
    }
}
