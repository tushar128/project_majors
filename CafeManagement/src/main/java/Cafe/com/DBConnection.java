package Cafe.com;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {
        
        String url = "jdbc:mysql://localhost:3306/cafe";
        String username = "root";
        String password = "password";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
