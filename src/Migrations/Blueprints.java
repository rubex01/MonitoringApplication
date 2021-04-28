package Migrations;

import java.sql.Statement;

public class Blueprints extends Migration {

    @Override
    public void runSQL() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE `monitoring`.`blueprints` ( `ID` INT NOT NULL AUTO_INCREMENT , `Object` TEXT NOT NULL, `Filename` VARCHAR(255) NOT NULL , PRIMARY KEY (`ID`)) ENGINE = InnoDB;");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
