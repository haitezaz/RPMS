package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorPatientAssignmentDAO {

    // Method to assign a doctor to a patient (patient name is passed directly)
    public static boolean assignDoctorToPatient(int patientID, String patientName, String doctorEmail) {
        String insertQuery = "INSERT INTO DoctorPatientAssignment (patient_ID, patient_name, DoctorEmail) VALUES (?, ?, ?)";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setInt(1, patientID);
            stmt.setString(2, patientName);
            stmt.setString(3, doctorEmail);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

