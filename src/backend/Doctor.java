package backend;

import java.util.ArrayList;

public class Doctor extends User {

    public Doctor( int doctorID, String doctorName, String email, String password) {
        super(doctorID, doctorName, email, password);

    }
    //doctor wants to see patients
    public ArrayList<Patient> getPatients() {
        ArrayList<Patient> doctorsPatients = AdminDAO.doctorGetsPatients(this.getEmail());
        return doctorsPatients;
    }

    //doctor wants to see a patients vitals
    public ArrayList<Vitals> doctorGetsVitals(int PatientID) {
        ArrayList<Vitals> patientVitals = VitalsDAO.getVitalsByPatientId(PatientID);
        return patientVitals;
    }

    //doctor gives feedback
    public void giveFeedback(int PatientID, String Feedback , String Medication) {
        Feedback newFeedback = new Feedback(PatientID , this.getUserId(), Feedback, Medication);
        FeedbackDAO.insertFeedback(newFeedback);

    }

    //doctor gives prescription
    public void givePrescription(int PatientID, String Prescription ) {
        Prescription newPrescription = new Prescription(PatientID , this.getUserId(), Prescription);
        PrescriptionDAO.insertPrescription(newPrescription);
    }

    //doctor wants to see requested appointments
    public ArrayList<Appointment> doctorGetsRequestedAppointments() {
        ArrayList<Appointment> requestedAppointments = AppointmentDAO.getPendingAppointmentsByDoctor(this.getEmail());
        return requestedAppointments;
    }
    //doctor wants to approve appointment
    public void acceptAppointment(int AppointmentID) {
        AppointmentDAO.approveAppointment(AppointmentID);
    }
    //doctor wants to see approved appointments
    public ArrayList<Appointment> doctorGetsApprovedAppointments() {
        ArrayList<Appointment> approvedAppointments = AppointmentDAO.getApprovedAppointmentsByDoctor(this.getEmail());
        return approvedAppointments;
    }

    /// /////////////////////////////////video calls//////////////////////////////////////////
    //Dotor wants to see requested video calls
    public ArrayList<VideoCall> doctorGetsRequestedVideoCalls() {
         ArrayList<VideoCall> requestedVideoCall =  VideocallDAO.getPendingVideoCalls(this.getEmail());
         return requestedVideoCall;
    }
    //doctor wants to Accept video call and give link
    public void acceptVideoCall(int VideoCallID , String VideoCallLink) {
        VideocallDAO.approveVideoCall(VideoCallID);
        VideocallDAO.addVideoLink(VideoCallID, VideoCallLink);

    }

    /// ////////////////////////SIGN IN AND SIGN OUT //////////////////////////
    public Doctor GetDoctorInstanceAndValidatePassword(String Email,String password) {
            Doctor newDoctor = AdminDAO.authenticateDoctor(Email, password);
            return newDoctor;
    }

    public Doctor SignUpDoctor( int doctorID, String doctorName, String email, String password) {
        Doctor newDoctor = new Doctor(doctorID , doctorName, email, password);
        AdminDAO.insertDoctor(newDoctor);
        return newDoctor;
    }



}