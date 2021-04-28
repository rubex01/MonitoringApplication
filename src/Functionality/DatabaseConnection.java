package Functionality;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static Connection dbConnection;

    private static final String connectString = "jdbc:mysql://localhost:3306/monitoring", username = "root", password = "";

    public static Connection getConnection() {
        if (dbConnection != null) return dbConnection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(connectString,username,password);
            return dbConnection;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void closeConnection() {
        try {
            dbConnection.close();
            dbConnection = null;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
