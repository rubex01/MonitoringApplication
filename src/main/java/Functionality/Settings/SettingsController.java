package Functionality.Settings;

import Assets.OkDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

public class SettingsController {

    private static final URL propertiesLocation = SettingsController.class.getResource("config.properties");

    private static Properties properties;

    private static ArrayList<String[]> QueueList;

    private static boolean showRestartOption = false;

    public static void addToQueue(String key, String value) {
        if (QueueList == null) QueueList = new ArrayList<>();
        QueueList.add(new String[]{key, value});
    }

    public static void executeQueue() {
        try {
            FileOutputStream out = new FileOutputStream(new File(propertiesLocation.toURI()));

            for (String[] change : QueueList) {
                properties.setProperty(change[0], change[1]);
            }

            properties.store(out, null);
            out.close();

            QueueList = null;
            if (showRestartOption) restartDialog();
        }
        catch (Exception exception) {
            // TODO: nice exception
            exception.printStackTrace();
        }
    }

    public static void changeSetting(String key, String value) {
        try {
            FileOutputStream out = new FileOutputStream(new File(propertiesLocation.toURI()));
            properties.setProperty(key, value);
            properties.store(out, null);
            out.close();

            if (showRestartOption) restartDialog();
        }
        catch (Exception exception) {
            // TODO: nice exception
            exception.printStackTrace();
        }
    }

    private static void propertiesLoader(boolean force) {
        if (properties != null && !force) return;
        try {
            properties = new Properties();
            properties.load(SettingsController.class.getResourceAsStream("config.properties"));
        }
        catch (Exception exception) {
            // TODO: nice exception
            exception.printStackTrace();
        }
    }

    private static void propertiesLoader() {
        propertiesLoader(false);
    }

    public static String getSetting(String key) {
        propertiesLoader();
        return properties.getProperty(key);
    }

    public static boolean isDefault(String key, String valueToCheck) {
        try {
            Properties defaultProps = new Properties();
            defaultProps.load(SettingsController.class.getResourceAsStream("default.properties"));

            return valueToCheck.equals(defaultProps.getProperty(key));
        }
        catch (Exception exception) {
            exception.printStackTrace();
            // TODO: nice exception
            return false;
        }
    }

    public static String getDefault(String key) {
        try {
            Properties inDefault = new Properties();
            inDefault.load(SettingsController.class.getResourceAsStream("default.properties"));

            return inDefault.getProperty(key);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            // TODO: nice exception
            return null;
        }
    }

    private static void restartDialog() {
        new OkDialog("Actie vereist", "De applicatie moet worden herstart om de gewijzigde instellingen in gebruik te nemen.", "Oke", OkDialog.WARNING);
        showRestartOption = false;
    }

    public static void setShowRestartOption(boolean showRestartOption) {
        SettingsController.showRestartOption = showRestartOption;
    }
}
