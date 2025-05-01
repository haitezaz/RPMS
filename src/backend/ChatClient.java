package backend;
public class ChatClient {
    private String name;
    private ChatServer server;

    public ChatClient(String name, ChatServer server)
    {
        this.name = name;
        this.server = server;
    }

    public void sendMessage(String message)
    {
        server.receiveMessage(name, message);
    }
}