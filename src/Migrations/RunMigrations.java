package Migrations;

import Functionality.DatabaseConnection;

import java.sql.Connection;
import java.util.ArrayList;

public class RunMigrations {

    private static ArrayList<Migration> migrations;

    private static Connection dbConnection;

    public static void runAllMigrations() {
        migrations = new ArrayList<>();
        addMigrations();
        dbConnection = DatabaseConnection.getConnection();
        runMigrations();
        try {
            dbConnection.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void addMigrations() {
        migrations.add(new Blueprints());
        // Add your migration file here
    }

    private static void runMigrations() {
        for (Migration migration : migrations) {
            migration.setConnection(dbConnection);
            migration.runSQL();
        }
    }

}
