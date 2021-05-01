package Functionality.Settings;

import GUI.Frame;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

public class SettingsController {

    private static final String propertiesLocation = "src/config.properties";

    private static final String defaultPropertiesLocation = "src/default.properties";

    private static Properties properties;

    private static ArrayList<String[]> QueueList;

    private static boolean showRestartOption = false;

    public static void addToQueue(String key, String value) {
        if (QueueList == null) QueueList = new ArrayList<>();
        QueueList.add(new String[]{key, value});
    }

    public static void executeQueue() {
        try {
            FileOutputStream out = new FileOutputStream(propertiesLocation);

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
            FileOutputStream out = new FileOutputStream(propertiesLocation);
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
            FileInputStream in = new FileInputStream(propertiesLocation);
            Properties props = new Properties();
            props.load(in);
            in.close();
            properties = props;
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
            FileInputStream inDefault = new FileInputStream(defaultPropertiesLocation);
            Properties propsDefault = new Properties();
            propsDefault.load(inDefault);
            inDefault.close();

            return valueToCheck.equals(propsDefault.getProperty(key));
        }
        catch (Exception exception) {
            exception.printStackTrace();
            // TODO: nice exception
            return false;
        }
    }

    public static String getDefault(String key) {
        try {
            FileInputStream inDefault = new FileInputStream(defaultPropertiesLocation);
            Properties propsDefault = new Properties();
            propsDefault.load(inDefault);
            inDefault.close();

            return propsDefault.getProperty(key);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            // TODO: nice exception
            return null;
        }
    }

    private static void restartDialog() {
        JOptionPane.showMessageDialog(Frame.defaultFrame, "De applicatie moet worden herstart om de gewijzigde instellingen in gebruik te nemen.");
        showRestartOption = false;
    }

    public static void setShowRestartOption(boolean showRestartOption) {
        SettingsController.showRestartOption = showRestartOption;
    }
}
