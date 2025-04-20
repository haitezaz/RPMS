package backend;

public class NotificationService {
    public static void sendNotificationToDoctor(int patient_id ,String patientAddress, String patientName ,  String DoctorEmail ) {
        String Subject = "Notification for a Patient's Critical Vitals";
        String Message = "Your patient with Patient ID = "+patient_id+ "with Name "+patientName+ " and Address: "+ patientAddress+ " has Critical Vitals visit him";
        EmailNotification.sendEmail(DoctorEmail, Subject, Message);
    }
    public static void sendNotificationToHospital(String patientAddress, String patientName,  String HospitalEmail ) {
        String Subject = "Notification for a Patient's Critical Vitals near your hospital";
        String Message = "A patient with Name "+patientName+ " and Address: "+ patientAddress +" has Critical Vitals visit him";
        EmailNotification.sendEmail(HospitalEmail, Subject, Message);
    }
    public static void sendNotificationToEmergencyContact(String patientAddress, String patientName,  String ContactEmail ) {
        String Subject = "Notification for a Patient's Critical Vitals";
        String Message = "A patient with Name "+patientName+ " and Address: "+ patientAddress +" has Critical Vitals visit him";
        EmailNotification.sendEmail(ContactEmail, Subject, Message);
    }
}
