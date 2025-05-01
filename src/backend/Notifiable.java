package backend;

public interface Notifiable {

//Exception Handling:
    void sendNotification(String recipient, String message, String subject) throws NotificationException;

}
