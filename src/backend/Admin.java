package backend;


import java.util.ArrayList;

public class Admin extends User {

    public Admin(int id , String name , String email, String password) {
        super(id , name , email , password);

    }


    //add new patient
    public  void addPatient(int patientID, String patientName, String email, String password, String assignedDoctoremail,
                                  String Address, String Gender, String EmergencyContact, String nearestHospitalEmail){
        Patient newPatient = new Patient(patientID, patientName, email, password, assignedDoctoremail,
                Address, Gender, EmergencyContact, nearestHospitalEmail);
        AdminDAO.addNewPatient(newPatient);
    }

    //add new doctor
    public void addDoctor( int doctorID, String doctorName, String email, String password) {
        Doctor newDoctor = new Doctor(doctorID, doctorName, email, password);
        AdminDAO.insertDoctor(newDoctor);
    }

    //delete patient
    public void deletePatient(int patientID){
        AdminDAO.deletePatientById(patientID);
    }
    //delete doctor
    public void deleteDoctor(int doctorID){
        AdminDAO.deleteDoctorById(doctorID);
    }
    //seeDoctors and patients inside the system
    public ArrayList<Patient> getPatients() {
        ArrayList<Patient> patients = AdminDAO.getAllPatients();
        return patients;
    }
    //sign in and instance
    public Admin validateAdminAndGetInstance(String email, String password) {
        Admin newAdmin = AdminDAO.authenticateAdmin(email, password);
        return newAdmin;
    }

    //Signup
    public Admin AdminSignUpAndGetInstance(int id , String Name ,String email, String password) {
        Admin newAdmin = new Admin(id , Name , email, password);
        AdminDAO.insertAdmin(newAdmin);
        return newAdmin;
    }
}
