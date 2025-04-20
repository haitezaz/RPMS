package backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/yourdbName";
    private static final String USER = "root"; // replace with your DB username
    private static final String PASSWORD = "dbPassword"; // replace with your DB password

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
