package backend;

public abstract class User {
    private int userId;
    protected String username;
    protected String email;
    protected String password;


    public User(int userId, String username, String email , String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public String getEmail(){
        return this.email;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {return password;}

}


