package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDAO {

    //method to add a new patient
    public static boolean addNewPatient(Patient patient) {
        String query = "INSERT INTO Patient (patient_ID, name, email, password, emergencyContactEmail, nearestHospitalEmail, Address, Gender, assigned_doctor_email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patient.getUserId());
            stmt.setString(2, patient.getUsername());
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getPassword());
            stmt.setString(5, patient.getEmergencyContact());
            stmt.setString(6, patient.getNearestHospitalEmail());
            stmt.setString(7, patient.getAddress());
            stmt.setString(8, patient.getGender());
            stmt.setString(9, patient.getAssignedDoctoremail());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }







    //method to add a new doctor
    public static boolean insertDoctor(Doctor doctor) {
        String query = "INSERT INTO Doctor (doctor_ID, name, email, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, doctor.getUserId());
            stmt.setString(2, doctor.getUsername());
            stmt.setString(3, doctor.getEmail());
            stmt.setString(4, doctor.getPassword());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





    //method to delete a patient
    public static boolean deletePatientById(int patientId) {
        String query = "DELETE FROM Patient WHERE patient_ID = ?";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    //method to delete a doctor
    public static boolean deleteDoctorById(int doctorId) {
        String query = "DELETE FROM Doctor WHERE doctor_ID = ?";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, doctorId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Doctor with ID " + doctorId + " deleted successfully.");
                return true;
            } else {
                System.out.println("No doctor found with ID " + doctorId);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //method to authenticate patient
    public static Patient authenticatePatient(String email, String password) {
        String query = "SELECT * FROM Patient WHERE email = ?";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                if (dbPassword.equals(password)) {
                    int patientID = rs.getInt("patient_ID");
                    String patientName = rs.getString("name");
                    String assignedDoctorEmail = rs.getString("assigned_doctor_email");
                    String address = rs.getString("Address");
                    String gender = rs.getString("Gender");
                    String emergencyContact = rs.getString("emergencyContactEmail");
                    String nearestHospitalEmail = rs.getString("nearestHospitalEmail");

                    return new Patient(patientID, patientName, email, password,
                            assignedDoctorEmail, address, gender,
                            emergencyContact, nearestHospitalEmail);
                }
            }
            else System.out.println("Invalid Password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // If credentials don't match or error occurs
    }
    //method for signing in of doctor
    public static Doctor authenticateDoctor(String email, String password) {
        String query = "SELECT * FROM Doctor WHERE email = ?";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                if (dbPassword.equals(password)) {
                    int doctorID = rs.getInt("doctor_ID");
                    String doctorName = rs.getString("name");

                    return new Doctor(doctorID, doctorName, email, password);
                } else {
                    System.out.println("Invalid Password");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // If credentials don't match or error occurs
    }


    //seeall patients
    public static ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patient";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int patientID = rs.getInt("patient_ID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String emergencyContact = rs.getString("emergencyContactEmail");
                String nearestHospitalEmail = rs.getString("nearestHospitalEmail");
                String address = rs.getString("Address");
                String gender = rs.getString("Gender");
                String assignedDoctorEmail = rs.getString("assigned_doctor_email");

                Patient patient = new Patient(patientID, name, email, password, assignedDoctorEmail,
                        address, gender, emergencyContact, nearestHospitalEmail);
                patients.add(patient);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }


    //doctor want to see assigned patients
    public static ArrayList<Patient> doctorGetsPatients(String doctorEmail) {
        ArrayList<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patient WHERE assigned_doctor_email = ?";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctorEmail);  // ✅ set doctorEmail before executing

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int patientID = rs.getInt("patient_ID");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String emergencyContact = rs.getString("emergencyContactEmail");
                    String nearestHospitalEmail = rs.getString("nearestHospitalEmail");
                    String address = rs.getString("Address");
                    String gender = rs.getString("Gender");
                    String assignedDoctorEmail = rs.getString("assigned_doctor_email");

                    Patient patient = new Patient(patientID, name, email, password, assignedDoctorEmail,
                            address, gender, emergencyContact, nearestHospitalEmail);
                    patients.add(patient);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }
    //Admin SignIn
    public static Admin authenticateAdmin(String email, String password) {
        String query = "SELECT * FROM Admin WHERE email = ?";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                if (dbPassword.equals(password)) {
                    int adminId = rs.getInt("admin_ID");
                    String name = rs.getString("name");
                    return new Admin(adminId, name, email, dbPassword);
                } else {
                    System.out.println("Invalid password.");
                }
            } else {
                System.out.println("Admin not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Signup
    public static boolean insertAdmin(Admin admin) {
        String query = "INSERT INTO Admin (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DataBaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getPassword());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
