package backend;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AppointmentDAO {

    // Insert new appointment requested by patient
    public static void insertAppointment(Appointment appointment) {
        String query = "INSERT INTO Appointment (DoctorEmail, patient_ID, dateTime, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, appointment.getDoctorEmail());
            stmt.setInt(2, appointment.getPatientId());
            stmt.setTimestamp(3, Timestamp.valueOf(appointment.getDateTime()));
            stmt.setString(4, appointment.getStatus());

            int rowsInserted = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    //doctor wants to see approved appointments
    // Doctor views all approved appointments
    public static ArrayList<Appointment> getApprovedAppointmentsByDoctor(String doctorEmail) {
        String query = "SELECT * FROM Appointment WHERE DoctorEmail = ? AND status = 'Approved' ORDER BY timeStamp DESC";
        ArrayList<Appointment> list = new ArrayList<>();

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctorEmail);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment(
                        rs.getString("DoctorEmail"),
                        rs.getInt("patient_ID"),
                        rs.getTimestamp("dateTime").toLocalDateTime()
                );
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update status to "Approved" by doctor
    public static boolean approveAppointment(int appointmentId) {
        String query = "UPDATE Appointment SET status = 'Approved' WHERE appointment_ID = ?";
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Patient views all appointments they requested
    public static ArrayList<Appointment> getRequestedAppointmentsByPatient(int patientId) {
        String query = "SELECT * FROM Appointment WHERE patient_ID = ? AND status = 'Pending' ORDER BY timeStamp DESC";
        ArrayList<Appointment> list = new ArrayList<>();
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment(
                        rs.getString("DoctorEmail"),
                        rs.getInt("patient_ID"),
                        rs.getTimestamp("dateTime").toLocalDateTime()
                );
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Doctor views all pending appointments
    public static ArrayList<Appointment> getPendingAppointmentsByDoctor(String doctorEmail) {
        String query = "SELECT * FROM Appointment WHERE DoctorEmail = ? AND status = 'Pending' ORDER BY timeStamp DESC";
        ArrayList<Appointment> list = new ArrayList<>();
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctorEmail);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment(
                        rs.getString("DoctorEmail"),
                        rs.getInt("patient_ID"),
                        rs.getTimestamp("dateTime").toLocalDateTime()
                );
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Patient views all approved appointments
    public static ArrayList<Appointment> getApprovedAppointmentsByPatient(int patientId) {
        String query = "SELECT * FROM Appointment WHERE patient_ID = ? AND status = 'Approved' ORDER BY timeStamp DESC";
        ArrayList<Appointment> list = new ArrayList<>();
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment(
                        rs.getString("DoctorEmail"),
                        rs.getInt("patient_ID"),
                        rs.getTimestamp("dateTime").toLocalDateTime()
                );
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
