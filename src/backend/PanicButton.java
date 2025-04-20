package backend;


public class PanicButton {
    //class to explicitly press panic button
    public static void sendNotificationToDoctor(int patient_id ,String patientAddress, String patientName ,  String DoctorEmail ) {
        String Subject = "Patient pressed Panic Button";
        String Message = "Your patient with Patient ID = "+patient_id+ "with Name "+patientName+ " and Address: "+ patientAddress+ " has Pressed Panic Button.";
        EmailNotification.sendEmail(DoctorEmail, Subject, Message);
    }
    public static void sendNotificationToHospital(String patientAddress, String patientName,  String HospitalEmail ) {
        String Subject = "Patient pressed Panic Button";
        String Message = "A patient with Name "+patientName+ " and Address: "+ patientAddress +" has Pressed Panic Button.";
        EmailNotification.sendEmail(HospitalEmail, Subject, Message);
    }
    public static void sendNotificationToEmergencyContact(String patientAddress, String patientName,  String ContactEmail ) {
        String Subject = "Patient pressed Panic Button";
        String Message = "A patient with Name "+patientName+ " and Address: "+ patientAddress +" has Pressed Panic Button.";
        EmailNotification.sendEmail(ContactEmail, Subject, Message);
    }
}
