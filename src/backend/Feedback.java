package backend;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Feedback {
    private int patientID;
    private int doctorID;
    private String feedback;
    private String medication;
    private LocalDateTime feedbackDateTime;

    public Feedback(int patientID, int doctorID, String feedback, String medication) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.feedback = feedback;
        this.medication = medication;

    }
    public void setFeedbackDateTime(LocalDateTime feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
    }
    public LocalDateTime getFeedbackDateTime() {return feedbackDateTime;}
    public int getPatientID() {return patientID;}
    public int getDoctorID() {return doctorID;}
    public String getFeedback() {return feedback;}
    public String getMedication() {return medication;}
}
