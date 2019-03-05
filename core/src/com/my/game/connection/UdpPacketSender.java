package com.my.game.connection;

import com.my.game.Properties;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UdpPacketSender implements Runnable {
    private final static int PORT = Integer.parseInt(Properties.loadConfigFile("PORT_UDP"));
    private DatagramSocket socket;
    private String hostname;

    UdpPacketSender(DatagramSocket socket, String hostname) {
        this.socket = socket;
        this.hostname = hostname;
    }

    public void sendMessage(JSONObject s) {
        byte buf[] = s.toString().getBytes();
        InetAddress address;
        try {
            address = InetAddress.getByName(hostname);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
            socket.send(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                while (!in.ready()) {
                    Thread.sleep(10);
                }
                //sendMessage(in.readLine());
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        }
    }
}