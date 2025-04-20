package backend;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FeedbackDAO {

    // Method to insert feedback by doctor
    public static boolean insertFeedback(Feedback feedback) {
        String query = "INSERT INTO Feedback (doctor_ID, patient_ID, feedback, medication) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, feedback.getDoctorID());
            stmt.setInt(2, feedback.getPatientID());
            stmt.setString(3, feedback.getFeedback());
            stmt.setString(4, feedback.getMedication());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method for patient to get feedbacks from doctors
    public static ArrayList<Feedback> getFeedbacksForPatient(int patientID) {
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT doctor_ID, patient_ID, feedback, medication, timeStamp FROM Feedback WHERE patient_ID = ? ORDER BY timeStamp DESC";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int doctorID = rs.getInt("doctor_ID");
                String feedbackText = rs.getString("feedback");
                String medication = rs.getString("medication");
                Timestamp timestamp = rs.getTimestamp("timeStamp");

                Feedback feedback = new Feedback(patientID, doctorID, feedbackText, medication);
                if (timestamp != null) {
                    feedback.setFeedbackDateTime(timestamp.toLocalDateTime());
                }
                feedbackList.add(feedback);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackList;
    }
}
