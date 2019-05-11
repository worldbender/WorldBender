package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Properties {
    private Properties(){}
    private static Logger logger = LogManager.getLogger(Properties.class.getName());
    private static final String PATH = "../../config.properties";
    private static final String TEST = "../../config.propertiesfortest";
    
    
    public static void createConfigFile() {
        java.util.Properties prop = new java.util.Properties();
        try (FileOutputStream output = new FileOutputStream(PATH)) {
            prop.put("IP","localhost");
            prop.put("PORT_TCP", "10008");
            prop.put("PORT_UDP", "7331");
            prop.store(output, "");
        } catch (Exception io) {
            logger.error(io.toString(), io);
        }
    }
    public static void createConfigFileForTesting() {
        java.util.Properties prop = new java.util.Properties();
        try (FileOutputStream output = new FileOutputStream(TEST)) {
            prop.put("PLAYER_TEXTURE_WIDTH", "31");
            prop.put("PLAYER_TEXTURE_HEIGHT", "36");
            prop.put("PLAYER_SCALE", "2");
            prop.store(output, "");
        } catch (Exception io) {
            logger.error(io.toString(), io);
        }
    }
    

    public static String loadConfigFile(String inputName) {

        java.util.Properties prop = new java.util.Properties();
        try (FileInputStream input = new FileInputStream(PATH)) {
            prop.load(input);
        } catch (IOException io) {
            logger.error(io.toString(), io);
        }
        return prop.getProperty(inputName);
    }
}
