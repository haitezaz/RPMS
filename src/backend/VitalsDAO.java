package backend;

import java.sql.*;
import java.util.ArrayList;

public class VitalsDAO {

    // Inserts new vitals into the database
    public static boolean insertVitals(Vitals vitals) {
        String query = "INSERT INTO Vitals (patient_ID, heartRate, systolicBloodPressure, diastolicBloodPressure, oxygenLevel, temperature) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vitals.getPatient_id());
            stmt.setDouble(2, vitals.getHeartRate());
            stmt.setDouble(3, vitals.getSystolicBloodPressure());
            stmt.setDouble(4, vitals.getDiastolicBloodPressure());
            stmt.setDouble(5, vitals.getOxygenLevel());
            stmt.setDouble(6, vitals.getTemperature());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Vitals> getVitalsByPatientId(int patientId) {
        ArrayList<Vitals> vitalsList = new ArrayList<>();
        String query = "SELECT * FROM Vitals WHERE patient_ID = ?";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vitals v = new Vitals(
                        rs.getInt("patient_ID"),
                        rs.getDouble("heartRate"),
                        rs.getDouble("systolicBloodPressure"),
                        rs.getDouble("diastolicBloodPressure"),
                        rs.getDouble("oxygenLevel"),
                        rs.getDouble("temperature")
                );

                Timestamp ts = rs.getTimestamp("timeStamp"); // assuming column name is `timestamp`
                if (ts != null) {
                    v.setDateTime(ts.toLocalDateTime());
                }

                vitalsList.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vitalsList;
    }

}
