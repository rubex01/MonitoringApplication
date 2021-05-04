package Migrations;

import Functionality.DatabaseConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class RunMigrations {

    private static String method;

    private final static Migration[] migrations = {
            new Blueprints()
    };

    public static void runAllMigrations(String method) {
        RunMigrations.method = method;

        runMigrations();
    }

    public static void runAllMigrations() {
        runAllMigrations("up");
    }

    private static void runMigrations() {
        for (Migration migration : migrations) {
            try {
                Statement stmt = DatabaseConnection.getConnection().createStatement();
                String query = (method.equals("up") ? migration.up() : migration.down());
                stmt.executeUpdate(query);
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        DatabaseConnection.closeConnection();
    }

}
