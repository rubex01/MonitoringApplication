package Functionality;

import Functionality.Settings.SettingsController;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static Connection dbConnection;

    private static String connectString, username, password;

    public static Connection getConnection() {
        connectString = "jdbc:mysql://" + SettingsController.getSetting("database_host") + ":3306/" + SettingsController.getSetting("database");
        username = SettingsController.getSetting("database_username");
        password = SettingsController.getSetting("database_password");

        if (dbConnection != null) return dbConnection;
        try {
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
