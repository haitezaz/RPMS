package backend;
import java.time.LocalDateTime;

public class Appointment {

    private String doctorEmail;
    private int patientId;
    private String status;
    private LocalDateTime dateTime;



    public Appointment(String doctorEmail, int patientId, LocalDateTime dateTime) {
        this.doctorEmail = doctorEmail;
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.status = "Pending";
    }


    public String getDoctorEmail() {return doctorEmail;};
    public int getPatientId() {
        return patientId;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public String getStatus() {
        return status;
    }
}
