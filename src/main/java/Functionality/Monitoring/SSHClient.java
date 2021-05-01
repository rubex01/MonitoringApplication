package Functionality.Monitoring;

import Functionality.Settings.SettingsController;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHClient {

    private static Session sshSession;

    private static void createConnection() {
        String host = SettingsController.getSetting("ssh_host");
        String user = SettingsController.getSetting("ssh_user");
        String password = SettingsController.getSetting("ssh_password");
        try{
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            sshSession = jsch.getSession(user, host, 22);
            sshSession.setPassword(password);
            sshSession.setConfig(config);
            sshSession.connect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Session getSession() {
        if (sshSession == null) createConnection();
        return sshSession;
    }

    public static void closeConnection() {
        sshSession.disconnect();
    }
}
