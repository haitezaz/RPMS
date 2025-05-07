package backend;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        int patientId = 1;
        int doctorId = 101;
        String doctorEmail = "doctor@example.com";
        String hospitalEmail = "hospital@example.com";
        String emergencyEmail = "emergency@example.com";
        String patientName = "abc";
        String patientAddress = "NUST";

        // Insert Vitals
        Vitals vitals = new Vitals(patientId, 78, 120, 80, 98.5, 36.7);
        vitals.setDateTime(LocalDateTime.now());
        boolean vitalsInserted = VitalsDAO.insertVitals(vitals);
        System.out.println("Vitals inserted: " + vitalsInserted);

        // Insert Prescription
        Prescription prescription = new Prescription(patientId, doctorId, "Paracetamol 500mg twice daily");
        boolean prescInserted = PrescriptionDAO.insertPrescription(prescription);
        System.out.println("Prescription inserted: " + prescInserted);

        // View Prescriptions
        List<Prescription> prescriptions = PrescriptionDAO.getPrescriptionsForPatient(patientId);
        for (Prescription p : prescriptions) {
            System.out.println("Prescription from Doctor ID " + p.getDoctor_id() + ": " + p.getPrescription());
        }

        // Trigger Panic Button
        PanicButton panicButton = new PanicButton();
        panicButton.pressPanicButton(patientId, patientName, patientAddress, doctorEmail, hospitalEmail, emergencyEmail);

        // Generate PDF Reports
        String vitalsReportPath = "VitalsReport_Patient" + patientId + ".pdf";
        String feedbackReportPath = "FeedbackReport_Patient" + patientId + ".pdf";

        PDFGeneration.generateVitalsReport(patientId, vitalsReportPath);
        PDFGeneration.generateFeedbackReport(patientId, feedbackReportPath);

        System.out.println("All tasks completed.");
    }
}
