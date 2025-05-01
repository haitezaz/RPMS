package backend;

import java.util.ArrayList;
import java.util.List;

public class ChatServer
{

    private List<String> Log;

    public ChatServer()
    {
        Log = new ArrayList<>();
    }

    public void receiveMessage(String sender, String message)
    {
        String formatted = sender + ": " + message;
        Log.add(formatted);
        System.out.println(formatted);
    }

    public List<String> getLog()
    {
        return Log;
    }
}