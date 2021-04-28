package Migrations;

import java.sql.Connection;

public abstract class Migration {

    protected Connection connection;

    public void setConnection(Connection dbConnection) {
        this.connection = dbConnection;
    }

    public abstract void runSQL();
}
