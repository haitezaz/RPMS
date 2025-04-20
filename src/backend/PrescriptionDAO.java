package backend;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PrescriptionDAO {

    // Method called by doctor to insert new prescription
    public static boolean insertPrescription(Prescription prescription) {
        String query = "INSERT INTO Prescription (doctor_ID, patient_ID, prescription) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, prescription.getDoctor_id());
            stmt.setInt(2, prescription.getPatient_id());
            stmt.setString(3, prescription.getPrescription());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method called by patient to see prescriptions
    public static ArrayList<Prescription> getPrescriptionsForPatient(int patient_id) {
        ArrayList<Prescription> prescriptionList = new ArrayList<>();
        String query = "SELECT doctor_ID, patient_ID, prescription, timeStamp FROM Prescription WHERE patient_ID = ? ORDER BY timeStamp DESC";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patient_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int doctor_id = rs.getInt("doctor_ID");
                String prescriptionText = rs.getString("prescription");
                Timestamp timestamp = rs.getTimestamp("timeStamp");

                Prescription p = new Prescription(patient_id, doctor_id, prescriptionText);
                if (timestamp != null) {
                    p.setPrescription_date_time(timestamp.toLocalDateTime());
                }
                prescriptionList.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prescriptionList;
    }
}
