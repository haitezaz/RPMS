package backend;

import java.time.LocalDateTime;

public class Prescription {
    private int patient_id;
    private int doctor_id;
    private String prescription;
    private LocalDateTime prescription_date_time;

    public Prescription(int patient_id, int doctor_id, String prescription) {
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.prescription = prescription;
    }

    public int getPatient_id() {return patient_id;}
    public int getDoctor_id() {return doctor_id;}
    public String getPrescription() {return prescription;}
    public LocalDateTime getPrescription_date_time() {return prescription_date_time;}
    public void setPrescription_date_time(LocalDateTime prescription_date_time) {
        this.prescription_date_time = prescription_date_time;
    }
}
