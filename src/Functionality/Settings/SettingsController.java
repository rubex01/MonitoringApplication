package Functionality.Settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

public class SettingsController {

    public static final String propertiesLocation = "src/config.properties";

    public static final String defaultPropertiesLocation = "src/default.properties";

    private static Properties properties;

    private static ArrayList<String[]> QueueList;

    public static void addToQueue(String key, String value) {
        if (QueueList == null) QueueList = new ArrayList<>();
        QueueList.add(new String[]{key, value});
    }

    public static void executeQueue() {
        try {
            properties = null;

            FileInputStream in = new FileInputStream(propertiesLocation);
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream(propertiesLocation);
            for (String[] change : QueueList) {
                props.setProperty(change[0], change[1]);
            }
            props.store(out, null);
            out.close();

            QueueList = null;
            propertiesLoader(true);
        }
        catch (Exception exception) {
            // TODO: nice exception
            exception.printStackTrace();
        }
    }

    public static void changeSetting(String key, String value) {
        try {
            FileInputStream in = new FileInputStream(propertiesLocation);
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream(propertiesLocation);
            props.setProperty(key, value);
            props.store(out, null);
            out.close();

            propertiesLoader(true);
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

}
