package Migrations;

public class Blueprints implements Migration {

    @Override
    public String up() {
        return "CREATE TABLE `monitoring`.`blueprints` ( `ID` INT NOT NULL AUTO_INCREMENT , `Object` LONGBLOB NOT NULL , `Filename` VARCHAR(255) NOT NULL , PRIMARY KEY (`ID`), UNIQUE (`Filename`)) ENGINE = InnoDB;";
    }

    @Override
    public String down() {
        return "DROP TABLE `monitoring`.`blueprints`";
    }

}
