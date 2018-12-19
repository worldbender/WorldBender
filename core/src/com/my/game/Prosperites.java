package com.my.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by lucyna on 26.11.18.
 */
public class Prosperites {

    private static final String PATH = "config.properties";

    public static void createConfigFile() {
        Properties prop = new Properties();
        FileOutputStream output = null;
        try {
            prop.put("ip","localhost");
            prop.put("PortTcp", "10008");
            prop.put("PortUdp", "7331");

            output = new FileOutputStream(PATH);
            prop.store(output, "program settings");
        } catch (Exception io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String loadConfigFile(String inputName) {

        Properties prop = new Properties();
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
                    e.printStackTrace();
                }
            }
        }
        return prop.getProperty(inputName);
    }

    public static void displayConfigFile(){

        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input  = new FileInputStream(PATH);
            prop.load(input);

            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                System.out.println("Key : " + key + ", Value : " + value);
            }

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }






    }



}
