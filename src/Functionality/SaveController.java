package Functionality;

import Functionality.Blueprint.Blueprint;
import Functionality.Blueprint.SaveOnlineDialog;
import GUI.Frame;

import javax.swing.*;
import java.io.*;
import java.sql.PreparedStatement;

public class SaveController {

    public static boolean saveBlueprint(Blueprint blueprint) {
        try {
            String defaultSavePath = blueprint.getFileTitle() + ".ser";
            if (blueprint.getSavePath() != null) defaultSavePath = blueprint.getSavePath();

            JFileChooser j = new JFileChooser();
            j.setSelectedFile(new File(defaultSavePath));
            int values = j.showSaveDialog(Frame.defaultFrame);

            if (j.getSelectedFile() == null || values == JFileChooser.CANCEL_OPTION) return false;

            String outputFilePath = j.getSelectedFile().getPath();
            String outputFileName = j.getSelectedFile().getName();
            String outputFileNameNoExtension = outputFileName;
            if (!outputFileName.substring((outputFileName.length()-4), outputFileName.length()).equals(".ser")) {
                outputFilePath = outputFilePath + ".ser";
            }
            else {
                outputFileNameNoExtension = outputFileNameNoExtension.substring(0, (outputFileNameNoExtension.length()-4));
            }

            blueprint.setTitle(outputFileNameNoExtension);
            blueprint.setSavePath(outputFilePath);
            blueprint.setOnlineSaved(false);
            blueprint.saved();

            FileOutputStream fileOut = new FileOutputStream(outputFilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(blueprint);
            out.close();
            fileOut.close();
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean openBlueprint() {
        try {
            JFileChooser j = new JFileChooser();
            int values = j.showOpenDialog(Frame.defaultFrame);

            if (j.getSelectedFile() == null || values == JFileChooser.CANCEL_OPTION || !j.getSelectedFile().exists()) return false;

            FileInputStream file = new FileInputStream(j.getSelectedFile().getPath());
            ObjectInputStream in = new ObjectInputStream(file);

            Blueprint blueprint = (Blueprint) in.readObject();

            in.close();
            file.close();

            Frame.defaultFrame.getTabsBar().addTab(blueprint);
            Frame.defaultFrame.getTabsBar().changeFocus(blueprint);
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean saveBlueprintOnline(Blueprint blueprint) {
        try {
            SaveOnlineDialog dialog = new SaveOnlineDialog(blueprint.getFileTitle());
            if (!dialog.getOkPressed()) return false;

            String filename = dialog.getFileName();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(blueprint);
            oos.flush();
            byte[] data = bos.toByteArray();

            if (dialog.hasToOverwrite()) {
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("update blueprints set Object = ? where Filename = ?");
                statement.setBytes(1, data);
                statement.setString(2, filename);
                statement.executeUpdate();
            }
            else {
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("insert into blueprints (Object, Filename) values (?, ?)");
                statement.setBytes(1, data);
                statement.setString(2, filename);
                statement.executeUpdate();
            }

            blueprint.setTitle(filename);
            blueprint.setOnlineSaved(true);
            blueprint.saved();

            DatabaseConnection.closeConnection();
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

}
