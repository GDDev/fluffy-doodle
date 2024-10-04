package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB = "shop";
    
    public static Connection getConnectionMySQL() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DB;
        final String USER = "root";
        final String PASSWORD = "";
        
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
