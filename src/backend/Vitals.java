package backend;


import java.time.LocalDateTime;

public class Vitals {
    private int patient_id;
    private double heartRate;
    private double systolicBloodPressure;
    private double diastolicBloodPressure;
    private double oxygenLevel;
    private double temperature;

    // Store the time of vitals recording
    private LocalDateTime dateTime;

    public Vitals(int patient_id ,double heartRate, double systolicBloodPressure, double diastolicBloodPressure, double oxygenLevel, double temperature ) {
        this.patient_id = patient_id;
        this.heartRate = heartRate;
        this.systolicBloodPressure = systolicBloodPressure;
        this.diastolicBloodPressure = diastolicBloodPressure;
        this.oxygenLevel = oxygenLevel;
        this.temperature = temperature;
    }

    public int getPatient_id() {return patient_id;}
    public double getHeartRate() { return heartRate; }
    public double getOxygenLevel() { return oxygenLevel; }
    public double getTemperature() { return temperature ; }
    public double getSystolicBloodPressure() { return systolicBloodPressure; }
    public double getDiastolicBloodPressure() {return diastolicBloodPressure;}

    public LocalDateTime getDateTime() {return dateTime;}
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}
}