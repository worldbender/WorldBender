package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Properties {
    private static Logger logger = LogManager.getLogger(Properties.class.getName());
    private static final String PATH = "../../config.properties";

    public static void createConfigFile() {
        java.util.Properties prop = new java.util.Properties();
        FileOutputStream output = null;
        try {
            prop.put("IP","localhost");
            prop.put("PORT_TCP", "10008");
            prop.put("PORT_UDP", "7331");

            output = new FileOutputStream(PATH);
        } catch (Exception io) {
            logger.error(io.toString(), io);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    logger.error(e.toString(), e);
                }
            }
        }
    }

    public static String loadConfigFile(String inputName) {

        java.util.Properties prop = new java.util.Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(PATH);
            prop.load(input);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error(e.toString(), e);
                }
            }
        }
        return prop.getProperty(inputName);
    }
}
