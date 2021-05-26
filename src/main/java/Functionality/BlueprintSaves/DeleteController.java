package Functionality.BlueprintSaves;

import Functionality.Blueprint.Blueprint;
import Functionality.DatabaseConnection;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;

public class DeleteController {
    public static boolean delBlueprintOnline(Blueprint blueprint) {
        try {
            DeleteOnlineDialog dialog = new DeleteOnlineDialog(blueprint.getFileTitle());
            if (!dialog.getOkPressed()) return false;
            String filename = dialog.getFileName();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(blueprint);
            oos.flush();
            byte[] data = bos.toByteArray();

            if (dialog.getOkPressed()) {
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("delete from blueprints where Filename = ?");
                statement.setString(1, filename);
                statement.execute();
            }

            DatabaseConnection.closeConnection();
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

    }
}
