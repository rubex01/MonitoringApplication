package Functionality.BlueprintSaves;

import Assets.YesNoDialog;
import Functionality.Blueprint.Blueprint;
import Functionality.DatabaseConnection;
import GUI.Frame;

import javax.swing.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SaveController {

    public static boolean saveBlueprint(Blueprint blueprint) {
        try {
            String defaultSavePath = blueprint.getFileTitle() + ".ser";
            if (blueprint.getSavePath() != null) defaultSavePath = blueprint.getSavePath();

            JFileChooser j = new JFileChooser();
            j.setSelectedFile(new File(defaultSavePath));
            int values = j.showSaveDialog(Frame.defaultFrame);

            if (j.getSelectedFile() == null || values == JFileChooser.CANCEL_OPTION) return false;

            if (j.getSelectedFile().exists()){
                YesNoDialog overwrite = new YesNoDialog("Overschrijven bestand", "Dit bestand bestaat al, wilt u het overschrijven?");
                if(overwrite.getCloseMethod() != YesNoDialog.YES_OPTION) {
                    return false;
                }
            }

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

    public static void openBlueprint(String path) {
        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);

            Blueprint blueprint = (Blueprint) in.readObject();

            in.close();
            file.close();

            Frame.defaultFrame.getTabsBar().addTab(blueprint);
            Frame.defaultFrame.getTabsBar().changeFocus(blueprint);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static boolean saveBlueprintOnline(Blueprint blueprint) {
        try {
            SaveOnlineDialog dialog = new SaveOnlineDialog(blueprint.getFileTitle());
            if (!dialog.getOkPressed()) return false;
            String filename = dialog.getFileName();

            if (dialog.hasToOverwrite()){
                YesNoDialog overwrite = new YesNoDialog("Overschrijven bestand", "Dit bestand bestaat al, wilt u het overschrijven?");
                if(overwrite.getCloseMethod() != YesNoDialog.YES_OPTION) return false;
            }

            blueprint.setTitle(filename);
            blueprint.setOnlineSaved(true);
            blueprint.saved();

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

            DatabaseConnection.closeConnection();
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean openBlueprintOnline(){
        try{
            OpenOnlineDialog dialog = new OpenOnlineDialog();
            if (!dialog.getOpenPressed()) return false;

            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("select Object from blueprints WHERE Filename = ?");
            statement.setString(1, dialog.getSelectedValue());
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                byte[] result = rs.getBytes(1);

                ByteArrayInputStream in = new ByteArrayInputStream(result);
                ObjectInputStream is = new ObjectInputStream(in);
                Blueprint blueprint = (Blueprint) is.readObject();

                Frame.defaultFrame.getTabsBar().addTab(blueprint);
                Frame.defaultFrame.getTabsBar().changeFocus(blueprint);
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
