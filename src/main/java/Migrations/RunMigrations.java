package Migrations;

import Functionality.DatabaseConnection;

import java.util.ArrayList;

public class RunMigrations {

    private static ArrayList<Migration> migrations;

    private static String method;

    public static void runAllMigrations(String method) {
        RunMigrations.method = method;

        migrations = new ArrayList<>();

        addMigrations();
        runMigrations();

        DatabaseConnection.closeConnection();
    }

    public static void runAllMigrations() {
        runAllMigrations("up");
    }

    private static void addMigrations() {
        migrations.add(new Blueprints());
        // Add your migration file here
    }

    private static void runMigrations() {
        for (Migration migration : migrations) {
            migration.setConnection(DatabaseConnection.getConnection());
            if (method.equals("up")) migration.runSQL();
            else if (method.equals("down")) migration.downSQL();
        }
    }

}
