package backend;

import java.util.ArrayList;
import java.util.List;

public class NotificationService
{

    private final List<Notifiable> channels = new ArrayList<>();

    public void addChannel(Notifiable channel)
    {
        channels.add(channel);
    }

    public void send(String recipient, String message, String subject) {
        for (Notifiable channel : channels)
        {
            try
            {
                channel.sendNotification(recipient, message, subject);
            }
            catch (NotificationException e)
            {
                System.err.println("Notification failure: " + e.getMessage());
            }
        }
    }
}