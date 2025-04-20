package backend;

import java.sql.*;
import java.util.ArrayList;

public class VideocallDAO {

    // 1. Method to insert a new video call request by a patient
    public static boolean requestVideoCall(int patientId, String doctorEmail) {
        String query = "INSERT INTO VideoCall (patient_ID, DoctorEmail, status) VALUES (?, ?, 'Pending')";
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            stmt.setString(2, doctorEmail);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Method for doctor to view all pending video calls
    public static ArrayList<VideoCall> getPendingVideoCalls(String doctorEmail) {
        ArrayList<VideoCall> calls = new ArrayList<>();
        String query = "SELECT * FROM VideoCall WHERE DoctorEmail = ? AND status = 'Pending' ORDER BY timeStamp DESC";
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, doctorEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VideoCall call = new VideoCall(
                        rs.getInt("patient_ID"),
                        rs.getString("DoctorEmail")

                );
                calls.add(call);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calls;
    }

    // 3. Method for doctor to approve a video call request
    public static boolean approveVideoCall(int videoCallID) {
        String query = "UPDATE VideoCall SET status = 'Approved' WHERE videoCallID = ?";
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, videoCallID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Method to insert video call link after doctor approves
    public static boolean addVideoLink(int videoCallID, String videoLink) {
        String query = "UPDATE VideoCall SET VideoLink = ? WHERE videoCallID = ?";
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, videoLink);
            stmt.setInt(2, videoCallID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Method for patient to get video call links for approved calls
    public static ArrayList<String> getApprovedVideoLinks(int patientId) {
        ArrayList<String> links = new ArrayList<>();
        String query = "SELECT VideoLink FROM VideoCall WHERE patient_ID = ? AND status = 'Approved' ORDER BY timeStamp DESC";
        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                links.add(rs.getString("VideoLink"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return links;
    }
}
