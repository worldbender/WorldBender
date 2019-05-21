package server.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.Properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PropertiesForTesting {
	
	public PropertiesForTesting(){}
	private static Logger logger = LogManager.getLogger(Properties.class.getName());
	private static final String PATH = "src/test/resources/config.properties";
	
	
	public void createConfigFileForTesting() {
		java.util.Properties prop = new java.util.Properties();
		try (FileOutputStream output = new FileOutputStream(PATH)) {
			prop.put("PLAYER_TEXTURE_WIDTH", "31");
			prop.put("PLAYER_TEXTURE_HEIGHT", "36");
			prop.put("PLAYER_SCALE", "2");
			prop.store(output, "");
		} catch (Exception io) {
			logger.error(io.toString(), io);
		}
	}
	
	public String loadConfigFileForTesting(String inputName) {
		
		java.util.Properties prop = new java.util.Properties();
		try (FileInputStream input = new FileInputStream(PATH)) {
			prop.load(input);
		} catch (IOException io) {
			logger.error(io.toString(), io);
		}
		return prop.getProperty(inputName);
	}
}
