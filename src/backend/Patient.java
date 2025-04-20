package backend;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Patient extends User{


    private String assignedDoctoremail;
    private String Address;
    private String Gender;
    private String EmergencyContact;
    private String nearestHospitalEmail;



    private boolean hasUploadedVitals;

    public Patient(int patientID, String patientName, String email, String password, String assignedDoctoremail,
                   String Address, String Gender, String EmergencyContact, String nearestHospitalEmail){
        super(patientID , patientName , email , password);


        this.assignedDoctoremail = assignedDoctoremail;
        this.Address = Address;
        this.Gender = Gender;
        this.EmergencyContact = EmergencyContact;
        this.nearestHospitalEmail = nearestHospitalEmail;
        hasUploadedVitals = false;
    }

    //////////////////////////////////////// === Vitals Management ===   ////////////////////////////////////////



    public void uploadVitalsCSV(String filePath, Connection conn) throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length != 5) {
                    throw new Exception("CSV must contain: heartRate,systolicBP,diastolicBP,oxygenLevel,temperature");
                }

                Vitals vitals = new Vitals(
                        this.getUserId(), // pass the patient_id
                        Double.parseDouble(nextLine[0]),  // heartRate
                        Double.parseDouble(nextLine[1]),  // systolicBP
                        Double.parseDouble(nextLine[2]),  // diastolicBP
                        Double.parseDouble(nextLine[3]),  // oxygenLevel
                        Double.parseDouble(nextLine[4])   // temperature
                );
                this.hasUploadedVitals = true;
                VitalsDAO.insertVitals(vitals);




                // Abnormal vitals logic
                if (vitals.getHeartRate() > 100 || vitals.getHeartRate() < 50 ||
                        vitals.getSystolicBloodPressure() > 140 || vitals.getSystolicBloodPressure() < 90 ||
                        vitals.getDiastolicBloodPressure() > 90 || vitals.getDiastolicBloodPressure() < 60 ||
                        vitals.getOxygenLevel() < 90 ||
                        vitals.getTemperature() > 100.4 || vitals.getTemperature() < 96.0) {

                    // Email notification to patient
                    EmergencyAlert.sendPatientEmail(this.getEmail());

                    // Email notification to doctor , emergency contact , nearest hospital

                    NotificationService.sendNotificationToDoctor(this.getUserId(),this.getAddress(), this.getUsername(), this.getAssignedDoctoremail());
                    NotificationService.sendNotificationToEmergencyContact(this.getAddress(), this.getUsername(), this.getEmergencyContact());
                    NotificationService.sendNotificationToHospital(this.getAddress(), this.getUsername(), this.getNearestHospitalEmail());

                }


            }

            hasUploadedVitals = true;
        }
    }

    /// ///////////////////////////////////////Appointments///////////////////////////////////////////
    public void requestAppointment(LocalDateTime dateTimeAppointment){
            Appointment newAppointment = new Appointment(this.getAssignedDoctoremail(),this.getUserId(), dateTimeAppointment );
            AppointmentDAO.insertAppointment(newAppointment);
    }

    public ArrayList<Appointment> getRequestedAppointments(){
        ArrayList<Appointment> requestedAppointmentsByPatient = AppointmentDAO.getRequestedAppointmentsByPatient(this.getUserId());
        return requestedAppointmentsByPatient;
    }

    public ArrayList<Appointment> getApprovedAppointments(){
        ArrayList<Appointment> approvedAppointments = AppointmentDAO.getApprovedAppointmentsByPatient(this.getUserId());
        return approvedAppointments;
    }


    /// ///////////////////////Feedback , medication , prescription///////////////////////////////////
    public ArrayList<Feedback> seeDoctorFeedback(){
        ArrayList<Feedback> doctorFeedback = FeedbackDAO.getFeedbacksForPatient(this.getUserId());
        return doctorFeedback;
    }

    public ArrayList<Prescription> seeDoctorPrescription(){
        ArrayList<Prescription> doctorPrescription = PrescriptionDAO.getPrescriptionsForPatient(this.getUserId());
        return doctorPrescription;
    }

    /// /////////////////////////////////PRESS PANIC BUTTON/////////////////////////////
    public void pressPanicButton(){
        PanicButton.sendNotificationToDoctor(getUserId(),getAddress(),getUsername() , getAssignedDoctoremail());
        PanicButton.sendNotificationToHospital(getAddress(), getUsername(), getAssignedDoctoremail());
        PanicButton.sendNotificationToEmergencyContact(getAddress(), getUsername(), getEmergencyContact());
    }

    /// /////////////////////////////VIDEO CALL///////////////////////////////////////
    public void requestVideoCall(){
        VideocallDAO.requestVideoCall(getUserId(),getAssignedDoctoremail());
    }

    public ArrayList<String> getLinkForApprovedVideoCall(){
        ArrayList<String> linkForApprovedVideoCall = VideocallDAO.getApprovedVideoLinks(this.getUserId());
        return linkForApprovedVideoCall;
    }








    /////////////////////////////////getters and setters//////////////////////////////////


    public String getAssignedDoctoremail() {return assignedDoctoremail;}
    public String getAddress() {return Address;}
    public String getGender() {return Gender;}
    public String getEmergencyContact() {return EmergencyContact;}
    public String getNearestHospitalEmail() {return nearestHospitalEmail;}
    public boolean getHasUploadedVitals() {return hasUploadedVitals;}


    //Method for signIN
    public static Patient getInstanceOfPateintAndValidatePassword(String patientEmail, String password) {
        Patient patient = AdminDAO.authenticatePatient(patientEmail, password);
        return patient;
    }

    //Method for SignUP
    public static Patient signUpPatient(int patientID, String patientName, String email, String password, String assignedDoctoremail,
                                        String Address, String Gender, String EmergencyContact, String nearestHospitalEmail){
        Patient newPatient = new Patient(patientID, patientName, email, password, assignedDoctoremail,
                Address, Gender, EmergencyContact, nearestHospitalEmail);
        AdminDAO.addNewPatient(newPatient);
        return newPatient;
    }
}


