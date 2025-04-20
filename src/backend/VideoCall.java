package backend;

import java.util.StringJoiner;

public class VideoCall {
    private int patient_id;
    private String doctorEmail;
    private String status;

    public VideoCall(int patient_id, String doctorEmail) {
        this.patient_id = patient_id;
        this.doctorEmail = doctorEmail;
        this.status = "Pending";
    }

    public int getPatient_id() {return patient_id;}
    public String getDoctorEmail() {return doctorEmail;}
    public String getStatus() {return status;}
}
