package com.my.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

public class Properties {
    private static Logger logger = LogManager.getLogger(Properties.class.getName());
    private static final String PATH = "../../config.properties";

    private Properties(){}

    public static void createConfigFile() {
        java.util.Properties prop = new java.util.Properties();
        try (FileOutputStream output = new FileOutputStream(PATH)) {
            prop.put("IP","localhost");
            prop.put("PORT_TCP", "10008");
            prop.put("PORT_UDP", "7331");

            prop.store(output, "program settings");
        } catch (Exception io) {
            logger.error(io.toString(), io);
        }
    }

    public static String loadConfigFile(String inputName) {

        java.util.Properties prop = new java.util.Properties();
        try(FileInputStream input = new FileInputStream(PATH)){
            prop.load(input);
        }catch (IOException io){
            logger.error(io.toString(), io);
        }

        try (FileInputStream input = new FileInputStream(PATH)) {
            prop.load(input);
        } catch (IOException io) {
            logger.error(io.toString(), io);
        }
        return prop.getProperty(inputName);
    }

    public static void displayConfigFile(){

        java.util.Properties prop = new java.util.Properties();
        try (FileInputStream input = new FileInputStream(PATH)){
            prop.load(input);

            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                System.out.println("Key : " + key + ", Value : " + value);
            }

        } catch (IOException io) {
            logger.error(io.toString(), io);
        }
    }
}
