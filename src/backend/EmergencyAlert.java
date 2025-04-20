package backend;

public class EmergencyAlert {
    //class to send emergency email to patients

    public static void sendPatientEmail(String PatientEmail){
        String subject = "Emergency Alert For Critical Values";
        String messageText = "Dear patient, you have abnormal vitals please visit your doctor urgently";
        EmailNotification.sendEmail(PatientEmail, subject, messageText);
    }

}
