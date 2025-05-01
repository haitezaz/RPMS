package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            // Setup DB connection (adjust credentials as per your environment)
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthdb", "root", "password");

            System.out.println("==== RHMS SYSTEM STARTED ====");

            // 1. Admin adds doctor and patient
            Admin admin = new Admin(1, "Haider", "admin@gmail.com", "admin123");

            admin.addDoctor(101, "Dr. abc", "doc@gmail.com", "docpass");
            admin.addPatient(201, "Peter", "erehman.bsai24seecs@seecs.edu.pk", "pass", 
                             "doc@gmail.com", "NUST", "Male", "911-1234", "hospital@gmail.com");

            System.out.println("Doctor and patient added by admin.");

            // 2. Patient signs in
            Patient patient = Patient.getInstanceOfPateintAndValidatePassword("erehman.bsai24seecs@seecs.edu.pk", "pass");
            if (patient == null) {
                System.out.println("Invalid patient credentials.");
                return;
            }

            System.out.println("Patient signed in: " + patient.getUsername());

            // 3. Patient uploads vitals from CSV
            String csvPath = "vitals.csv"; // in the working directory
            patient.uploadVitalsCSV(csvPath, conn);
            System.out.println("Vitals uploaded and evaluated.");

            // 4. Doctor signs in and views patient's vitals
            Doctor doctor = new Doctor(101, "Dr. abc", "doc@gmail.com", "docpass");
            ArrayList<Patient> patients = doctor.getPatients();
            System.out.println("Doctor's patients: ");
            for (Patient p : patients) {
                System.out.println(p.getUsername());
            }

            ArrayList<Vitals> vitalsList = doctor.doctorGetsVitals(patient.getUserId());
            System.out.println("Patient Vitals:");
            for (Vitals v : vitalsList) {
                System.out.println(v);
            }

            // 5. Patient requests appointment
            patient.requestAppointment(LocalDateTime.of(2025, 5, 2, 10, 0));
            System.out.println("Patient requested appointment.");

            // 6. Doctor views and approves appointment
            ArrayList<Appointment> requests = doctor.doctorGetsRequestedAppointments();
            if (!requests.isEmpty()) {
                int apptId = requests.get(0).getAppointmentId();
                doctor.acceptAppointment(apptId);
                System.out.println("Doctor approved appointment ID: " + apptId);
            }

            // 7. Doctor gives feedback and prescription
            doctor.giveFeedback(patient.getUserId(), "Stay hydrated", "Paracetamol 500mg");
            doctor.givePrescription(patient.getUserId(), "Paracetamol 500mg twice a day");

            // 8. Patient sees feedback and prescriptions
            ArrayList<Feedback> feedbackList = patient.seeDoctorFeedback();
            System.out.println("Feedback from doctor:");
            for (Feedback f : feedbackList) {
                System.out.println(f);
            }

            ArrayList<Prescription> prescriptions = patient.seeDoctorPrescription();
            System.out.println("Prescriptions:");
            for (Prescription p : prescriptions) {
                System.out.println(p);
            }

            // 9. Patient presses panic button
            System.out.println("Patient presses panic button:");
            patient.pressPanicButton();

            // 10. Video call request
            patient.requestVideoCall();
            System.out.println("Video call requested.");

            // 11. Generate patient report
            ReportPDFGenerator reportGenerator = new ReportPDFGenerator();
            reportGenerator.generatePatientReport(patient, "patient_report.pdf");
            System.out.println("Patient report generated.");

            // 12. Generate vitals chart
            VitalsChartGenerator chartGenerator = new VitalsChartGenerator();
            chartGenerator.generateVitalsChart(patient.getVitals(), "patient_vitals_chart.png");
            System.out.println("Vitals chart generated.");

            System.out.println("==== RHMS SYSTEM EXECUTION COMPLETE ====");

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
